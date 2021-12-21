# SECTION 04. MVC 프레임워크 만들기


## 프론트 컨트롤러 패턴 소개

* FrontController 패턴 특징
    - 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음 
    - 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출 
    - 입구를 하나로!
    - 공통 처리 가능
    - 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨

* 스프링 웹 MVC와 프론트 컨트롤러
    - 스프링 웹 MVC의 핵심도 바로 FrontController
    - 스프링 웹 MVC의 DispatcherServlet이 FrontController 패턴으로 구현되어 있음


## 프론트 컨트롤러 도입 - v1

* v1 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> url 매핑 정보에서 컨트롤러 조회 -> 컨트롤러 호출
3. 컨트롤러가 jsp forward -> jsp가 html 응답

* ControllerV1 생성
    - 서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입한다. 각 컨트롤러들은 이 인터페이스를 구현하면 된다. 프론트 컨트롤러는 이 인터페이스를 호출해서 구현과 관계없이 로직의 일관성을 가져갈 수 있다.
    - 이제 이 인터페이스를 구현한 컨트롤러를 만들어보자. 지금 단계에서는 기존 로직을 최대한 유지하는게 핵심이다.

* MemberSaveControllerV1 - 회원 저장 컨트롤러,  MemberListControllerV1 - 회원 목록 컨트롤러 생성

* FrontControllerServletV1 - 프론트 컨트롤러 생성

* 프론트 컨트롤러 분석

    * urlPatterns
        - urlPatterns = "/front-controller/v1/*" : /front-controller/v1 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
        - 예) /front-controller/v1 , /front-controller/v1/a , /front-controller/v1/a/b

    * controllerMap
        - key: 매핑 URL
        - value: 호출될 컨트롤러

    * service()
        - 먼저 requestURI 를 조회해서 실제 호출할 컨트롤러를 controllerMap 에서 찾는다. 만약 없다면 404(SC_NOT_FOUND) 상태 코드를 반환한다.
        - 컨트롤러를 찾고 controller.process(request, response); 을 호출해서 해당 컨트롤러를 실행한다.

    * JSP
    - JSP는 이전 MVC에서 사용했던 것을 그대로 사용한다.

    * 실행
    - 결과: 기존 서블릿, JSP로 만든 MVC와 동일하게 실행 되는 것을 확인


## View 분리 - v2

- 모든 컨트롤러에서 뷰로 이동하는 부분에 중복이 있고, 깔끔하지 않음
    - -> 이 부분을 깔끔하게 분리하기 위해 별도로 뷰를 처리하는 객체를 만들자

* v2 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> url 매핑 정보에서 컨트롤러 조회 -> 컨트롤러 호출
3. 컨트롤러가 프론트 컨트롤러에 MyView 반환
4. 프론트컨트롤러가 render() 호출 -> MyView
5. MyView에서 JSP forward
6. html 응답

* MyView 생성

* ControllerV2 인터페이스
    - 컨트롤러가 뷰를 반환하는 특징이 있다.

* MemberFormControllerV2 - 회원 등록 폼, MemberSaveControllerV2 - 회원 저장, MemberListControllerV2 - 회원 목록 생성
    - 이제 각 컨트롤러는 복잡한 dispatcher.forward() 를 직접 생성해서 호출하지 않아도 된다. 단순히 MyView 객체를 생성하고 거기에 뷰 이름만 넣고 반환하면 된다.
    - return new MyView("/WEB-INF/views/new-form.jsp");

* 프론트 컨트롤러 V2
    - ControllerV2의 반환 타입이 MyView 이므로 프론트 컨트롤러는 컨트롤러의 호출 결과로 MyView 를 반환 받는다. 그리고 view.render() 를 호출하면 forward 로직을 수행해서 JSP가 실행된다.
        - dispatcher.forward(request, response);가 컨트롤러에서 마이뷰로 이동

* 프론트 컨트롤러의 도입으로 MyView 객체의 render() 를 호출하는 부분을 모두 일관되게 처리할 수 있다. 각각의 컨트롤러는 MyView 객체를 생성만 해서 반환하면 된다.


## Model 추가 - v3

* 서블릿 종속성 제거

컨트롤러 입장에서 HttpServletRequest, HttpServletResponse이 꼭 필요할까?
요청 파라미터 정보는 자바의 Map으로 대신 넘기도록 하면 지금 구조에서는 컨트롤러가 서블릿 기술을 몰라도 동작할 수 있다.
그리고 request 객체를 Model로 사용하는 대신에 별도의 Model 객체를 만들어서 반환하면 된다. 우리가 구현하는 컨트롤러가 서블릿 기술을 전혀 사용하지 않도록 변경해보자.
이렇게 하면 구현 코드도 매우 단순해지고, 테스트 코드 작성이 쉽다.

* 뷰 이름 중복 제거

컨트롤러에서 지정하는 뷰 이름에 중복이 있는 것을 확인할 수 있다.
컨트롤러는 뷰의 논리 이름을 반환하고, 실제 물리 위치의 이름은 프론트 컨트롤러에서 처리하도록 단순화 하자.
이렇게 해두면 향후 뷰의 폴더 위치가 함께 이동해도 프론트 컨트롤러만 고치면 된다.

/WEB-INF/views/new-form.jsp new-form 
/WEB-INF/views/save-result.jsp save-result 
/WEB-INF/views/members.jsp members

* V3 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> url 매핑 정보에서 컨트롤러 조회 -> 컨트롤러 호출
3. 컨트롤러가 프론트 컨트롤러에 ModelView 반환
4. 프론트컨트롤러가 viewResolver 호출
5. viewResolver가 MyView 반환
6. 프론트컨트롤러가 render(model) 호출 -> MyView
5. MyView에서 html 응답

* ModelView
    - 지금까지 컨트롤러에서 서블릿에 종속적인 HttpServletRequest를 사용했다. 그리고 Model도 request.setAttribute() 를 통해 데이터를 저장하고 뷰에 전달했다.
    - 서블릿의 종속성을 제거하기 위해 Model을 직접 만들고, 추가로 View 이름까지 전달하는 객체를 만들어보자.
    - (이번 버전에서는 컨트롤러에서 HttpServletRequest를 사용할 수 없다. 따라서 직접 request.setAttribute() 를 호출할 수 도 없다. 따라서 Model이 별도로 필요하다.)
    - 참고로 ModelView 객체는 다른 버전에서도 사용하므로 패키지를 frontcontroller 에 둔다.

* ModelView 생성
    - 뷰의 이름과 뷰를 렌더링할 때 필요한 model 객체를 가지고 있다. model은 단순히 map으로 되어 있으므로 컨트롤러에서 뷰에 필요한 데이터를 key, value로 넣어주면 된다.

* ControllerV3 생성
    - 이 컨트롤러는 서블릿 기술을 전혀 사용하지 않는다. 따라서 구현이 매우 단순해지고, 테스트 코드 작성시 테스트 하기 쉽다.
    - HttpServletRequest가 제공하는 파라미터는 프론트 컨트롤러가 paramMap에 담아서 호출해주면 된다. 응답 결과로 뷰 이름과 뷰에 전달할 Model 데이터를 포함하는 ModelView 객체를 반환하면 된다.

* MemberFormControllerV3 - 회원 등록 폼
    - ModelView 를 생성할 때 new-form 이라는 view의 논리적인 이름을 지정한다. 실제 물리적인 이름은 프론트 컨트롤러에서 처리한다.

* MemberSaveControllerV3 - 회원 저장
    - paramMap.get("username");
        - 파라미터 정보는 map에 담겨있다. map에서 필요한 요청 파라미터를 조회하면 된다. 
    - mv.getModel().put("member", member);
        - 모델은 단순한 map이므로 모델에 뷰에서 필요한 member 객체를 담고 반환한다.

* FrontControllerServletV3
    - createParamMap() 추가
        - HttpServletRequest에서 파라미터 정보를 꺼내서 Map으로 변환한다. 그리고 해당 Map( paramMap )을 컨트롤러에 전달하면서 호출한다.

    * 뷰 리졸버
    - MyView view = viewResolver(viewName)
    - 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경한다. 그리고 실제 물리 경로가 있는 MyView 객체를 반환한다.
        - 논리 뷰 이름: members
        - 물리 뷰 경로: /WEB-INF/views/members.jsp
    
    - view.render(mv.getModel(), request, response)
        - 뷰 객체를 통해서 HTML 화면을 렌더링 한다.
        - 뷰 객체의 render() 는 모델 정보도 함께 받는다.
        - JSP는 request.getAttribute() 로 데이터를 조회하기 때문에, 모델의 데이터를 꺼내서 request.setAttribute() 로 담아둔다.
        - JSP로 포워드 해서 JSP를 렌더링 한다.


## 단순하고 실용적인 컨트롤러 - v4

- 앞서 만든 v3 컨트롤러는 잘 설계된 컨트롤러이다. 그런데 실제 컨트톨러 인터페이스를 구현하는 개발자 입장에서 보면, 항상 ModelView 객체를 생성하고 반환해야 하는 부분이 조금은 번거롭다.
- 이번에는 v3를 조금 변경해서 실제 구현하는 개발자들이 매우 편리하게 개발할 수 있는 v4 버전을 개발해보자.

* V4 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> url 매핑 정보에서 컨트롤러 조회 -> 컨트롤러 호출(paramMap,model)
3. 컨트롤러가 프론트 컨트롤러에 ViewName 반환
4. 프론트컨트롤러가 viewResolver 호출
5. viewResolver가 MyView 반환
6. 프론트컨트롤러가 render(model) 호출 -> MyView
5. MyView에서 html 응답

* ControllerV4
-  인터페이스에 ModelView가 없다. model 객체는 파라미터로 전달되기 때문에 그냥 사용하면 되고, 결과로 뷰의 이름만 반환해주면 된다.

* MemberFormControllerV4
- 정말 단순하게 new-form 이라는 뷰의 논리 이름만 반환하면 된다.

* MemberSaveControllerV4
    - model.put("member", member)
        - 모델이 파라미터로 전달되기 때문에, 모델을 직접 생성하지 않아도 된다.

* FrontControllerServletV4
    - FrontControllerServletV4 는 사실 이전 버전과 거의 동일하다.

    * 모델 객체 전달
        - Map<String, Object> model = new HashMap<>(); //추가
        - 모델 객체를 프론트 컨트롤러에서 생성해서 넘겨준다. 컨트롤러에서 모델 객체에 값을 담으면 여기에 그대로 담겨있게 된다.

    * 뷰의 논리 이름을 직접 반환
        - String viewName = controller.process(paramMap, model);
        - MyView view = viewResolver(viewName);
            - 컨트롤러가 직접 뷰의 논리 이름을 반환하므로 이 값을 사용해서 실제 물리 뷰를 찾을 수 있다.
    
## 유연한 컨트롤러1 - v5

* 어댑터 패턴
지금까지 우리가 개발한 프론트 컨트롤러는 한가지 방식의 컨트롤러 인터페이스만 사용할 수 있다. ControllerV3 , ControllerV4 는 완전히 다른 인터페이스이다. 따라서 호환이 불가능하다. 마치 v3는 110v이고, v4는 220v 전기 콘센트 같은 것이다. 이럴 때 사용하는 것이 바로 어댑터이다.
어댑터 패턴을 사용해서 프론트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 변경해보자.

* V5 구조

1. 클라이언트 -> 프론트 컨트롤러에 http 요청
2. 프론트 컨트롤러 -> 핸들러 어댑터 목록에서 핸들러를 처리할 수 있는 어댑터 조회 -> handle(handler)로 핸들러 어댑터를 통해 핸들러 호출
3. 핸들러 어댑터가 프론트 컨트롤러에 ModelView 반환
4. 프론트컨트롤러가 viewResolver 호출
5. viewResolver가 MyView 반환
6. 프론트컨트롤러가 render(model) 호출 -> MyView
5. MyView에서 html 응답

    * 핸들러 어댑터
    : 중간에 어댑터 역할을 하는 어댑터가 추가되었는데 이름이 핸들러 어댑터이다. 여기서 어댑터 역할을 해주는 덕분에 다양한 종류의 컨트롤러를 호출할 수 있다.

    * 핸들러
    : 컨트롤러의 이름을 더 넓은 범위인 핸들러로 변경했다. 그 이유는 이제 어댑터가 있기 때문에 꼭 컨트롤러의 개념 뿐만 아니라 어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리할 수 있기 때문이다.

* MyHandlerAdapter 인터페이스

    -  boolean supports(Object handler)
        - handler는 컨트롤러를 말한다.
        - 어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메서드다.

    -  ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
        - 어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView를 반환해야 한다.
        - 실제 컨트롤러가 ModelView를 반환하지 못하면, 어댑터가 ModelView를 직접 생성해서라도 반환해야 한다.
        - 이전에는 프론트 컨트롤러가 실제 컨트롤러를 호출했지만 이제는 이 어댑터를 통해서 실제 컨트롤러가 호출된다.

* ControllerV3HandlerAdapter
    - ControllerV3 을 처리할 수 있는 어댑터를 뜻한다.

* FrontControllerServletV5

    * 컨트롤러(Controller) 핸들러(Handler)
        - 이전에는 컨트롤러를 직접 매핑해서 사용했다. 그런데 이제는 어댑터를 사용하기 때문에, 컨트롤러 뿐만 아니라 어댑터가 지원하기만 하면, 어떤 것이라도 URL에 매핑해서 사용할 수 있다. 그래서 이름을 컨트롤러에서 더 넒은 범위의 핸들러로 변경했다.

    * 생성자
        - 생성자는 핸들러 매핑과 어댑터를 초기화(등록)한다.

     * 매핑 정보
        - 매핑 정보의 값이 ControllerV3 , ControllerV4 같은 인터페이스에서 아무 값이나 받을 수 있는 Object 로 변경되었다.
    
    * 핸들러 매핑
        - Object handler = getHandler(request)
            - 핸들러 매핑 정보인 handlerMappingMap 에서 URL에 매핑된 핸들러(컨트롤러) 객체를 찾아서 반환한다.

    * 핸들러를 처리할 수 있는 어댑터 조회
        - handler 를 처리할 수 있는 어댑터를 adapter.supports(handler) 를 통해서 찾는다. handler가 ControllerV3 인터페이스를 구현했다면, ControllerV3HandlerAdapter 객체가 반환된다.

    * 어댑터 호출
        - ModelView mv = adapter.handle(request, response, handler);
            - 어댑터의 handle(request, response, handler) 메서드를 통해 실제 어댑터가 호출된다. 어댑터는 handler(컨트롤러)를 호출하고 그 결과를 어댑터에 맞추어 반환한다. 
            - ControllerV3HandlerAdapter 의 경우 어댑터의 모양과 컨트롤러의 모양이 유사해서 변환 로직이 단순하다.

## 유연한 컨트롤러2 - v5

- FrontControllerServletV5 에 ControllerV4 기능도 추가해보자.
    - 핸들러 매핑( handlerMappingMap )에 ControllerV4 를 사용하는 컨트롤러를 추가하고, 해당 컨트롤러를 처리할 수 있는 어댑터인 ControllerV4HandlerAdapter 도 추가하자.

* ControllerV4HandlerAdapter
    - handler 가 ControllerV4 인 경우에만 처리하는 어댑터이다.

    * 실행 로직
    - handler를 ControllerV4로 케스팅 하고, paramMap, model을 만들어서 해당 컨트롤러를 호출한다. 그리고 viewName을 반환 받는다.

    * 어댑터 변환
    - 어댑터가 호출하는 ControllerV4 는 뷰의 이름을 반환한다. 그런데 어댑터는 뷰의 이름이 아니라 ModelView 를 만들어서 반환해야 한다. 여기서 어댑터가 꼭 필요한 이유가 나온다.
    - ControllerV4 는 뷰의 이름을 반환했지만, 어댑터는 이것을 ModelView로 만들어서 형식을 맞추어 반환한다. 마치 110v 전기 콘센트를 220v 전기 콘센트로 변경하듯이! 


## 정리

* v1: 프론트 컨트롤러를 도입
    - 기존 구조를 최대한 유지하면서 프론트 컨트롤러를 도입

* v2: View 분류
    - 단순 반복 되는 뷰 로직 분리

* v3: Model 추가 서블릿 종속성 제거
    - 뷰 이름 중복 제거

* v4: 단순하고 실용적인 컨트롤러 v3와 거의 비슷
    - 구현 입장에서 ModelView를 직접 생성해서 반환하지 않도록 편리한 인터페이스 제공
    
* v5: 유연한 컨트롤러 어댑터 도입
    - 어댑터를 추가해서 프레임워크를 유연하고 확장성 있게 설계