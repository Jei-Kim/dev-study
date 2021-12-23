# SECTION 05. 스프링 MVC - 구조 이해


## 스프링 MVC 전체 구조

* 직접 만든 프레임워크(section04) vs 스프링 MVC 비교 
    - FrontController -> DispatcherServlet 
    - handlerMappingMap -> HandlerMapping 
    - MyHandlerAdapter -> HandlerAdapter 
    - ModelView -> ModelAndView 
    - viewResolver -> ViewResolver
    - MyView -> View

* DispatcherServlet 구조 살펴보기

    - 스프링 MVC도 프론트 컨트롤러 패턴으로 구현되어 있다.
    - 스프링 MVC의 프론트 컨트롤러가 바로 디스패처 서블릿(DispatcherServlet)이다. 
    - 그리고 이 디스패처 서블릿이 바로 스프링 MVC의 핵심이다.

    * DispacherServlet 서블릿 등록
        - DispacherServlet 도 부모 클래스에서 HttpServlet 을 상속 받아서 사용하고, 서블릿으로 동작한다.
        - 스프링 부트는 DispacherServlet 을 서블릿으로 자동으로 등록하면서 모든 경로( urlPatterns="/" )에 대해서 매핑한다.

    * 요청 흐름
        - 서블릿이 호출되면 HttpServlet 이 제공하는 serivce() 가 호출된다.
        - 스프링 MVC는 DispatcherServlet 의 부모인 FrameworkServlet 에서 service() 를 오버라이드 해두었다.
        - FrameworkServlet.service() 를 시작으로 여러 메서드가 호출되면서 DispacherServlet.doDispatch() 가 호출된다.

* DispacherServlet.doDispatch() 분석

1. 핸들러 조회
```
mappedHandler = getHandler(processedRequest); 
    if (mappedHandler == null) {
        noHandlerFound(processedRequest, response);
        return; 
    }
```

2. 핸들러 어댑터 조회 - 핸들러를 처리할 수 있는 어댑터
```
HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
```

3. 핸들러 어댑터 실행 ->
4. 핸들러 어댑터를 통해 핸들러 실행
5. ModelAndView 반환
```
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
  processDispatchResult(processedRequest, response, mappedHandler, mv,
dispatchException);
}

private void processDispatchResult(HttpServletRequest request,
HttpServletResponse response, HandlerExecutionChain mappedHandler, ModelAndView
mv, Exception exception) throws Exception {

// 뷰 렌더링 호출
render(mv, request, response);
}
protected void render(ModelAndView mv, HttpServletRequest request,
HttpServletResponse response) throws Exception {
  View view;
String viewName = mv.getViewName();

```

6. 뷰 리졸버를 통해서 뷰 찾기 ->
7. View 반환
```
  view = resolveViewName(viewName, mv.getModelInternal(), locale, request);
```

8. 뷰 렌더링
```
 view.render(mv.getModelInternal(), request, response);
}
```

* SpringMVC 동작 순서

1. 핸들러 조회
    : 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.

2. 핸들러 어댑터 조회
    : 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.

3. 핸들러 어댑터 실행
    : 핸들러 어댑터를 실행한다.

4. 핸들러 실행
    : 핸들러 어댑터가 실제 핸들러를 실행한다.

5. ModelAndView 반환
    : 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환해서 반환한다.

6. viewResolver 호출
    : 뷰 리졸버를 찾고 실행한다.
    - JSP의 경우: InternalResourceViewResolver 가 자동 등록되고, 사용된다.

7. View반환
    : 뷰리졸버는 뷰의 논리이름을 물리이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체를 반환한다.
    - JSP의 경우 InternalResourceView(JstlView) 를 반환하는데, 내부에 forward() 로직이 있다.

8. 뷰렌더링
    :뷰를 통해서 뷰를 렌더링한다.



## 핸들러 매핑과 핸들러 어댑터

* 과거 버전 스프링 컨트롤러: 컨트롤러 인터페이스
    - OldController 생성, 구현

    * HandlerMapping(핸들러 매핑)
    - 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 한다.
        - BeanNameUrlHandlerMapping

    * HandlerAdapter(핸들러 어댑터)
    - 핸들러 매핑을 통해서 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.
        - SimpleControllerHandlerAdapter

* HttpRequestHandler
    - 핸들러 매핑과, 어댑터를 더 잘 이해하기 위해 Controller 인터페이스가 아닌 다른 핸들러를 알아보자. HttpRequestHandler 핸들러(컨트롤러)는 서블릿과 가장 유사한 형태의 핸들러이다.
        - MyHttpRequestHandler 생성, 구현

    * 순서

        1. 핸들러 매핑으로 핸들러 조회
            - BeanNameUrlHandlerMapping

        2. 핸들러 어댑터 조회
            - HandlerAdapter 의 supports() 를 순서대로 호출한다.
            - HttpRequestHandlerAdapter 가 HttpRequestHandler 인터페이스를 지원하므로 대상이 된다.

        3. 핸들러 어댑터 실행
            - 디스패처 서블릿이 조회한 HttpRequestHandlerAdapter 를 실행하면서 핸들러 정보도 함께 넘겨준다.
            - HttpRequestHandlerAdapter 는 핸들러인 MyHttpRequestHandler 를 내부에서 실행하고, 그 결과를 반환한다.

* @RequestMapping
    - 가장 우선순위가 높은 핸들러 매핑과 핸들러 어댑터는 RequestMappingHandlerMapping, RequestMappingHandlerAdapter이다.
    - 실무에서 99.9% 이 방식의 컨트롤러를 사용한다.



## 뷰 리졸버

- OldController - View 조회할 수 있도록 변경
- application.properties에 view prefix, suffix 코드 추가

* 뷰 리졸버 동작 방식

    * 스프링 부트가 자동으로 등록하는 뷰 리졸버
    
    - 1 = BeanNameViewResolver : 빈 이름으로 뷰를 찾아서 반환한다. 
                                    (예: 엑셀 파일 생성 기능에 사용)
    - 2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.

1. 핸들러 어댑터 호출
    : 핸들러 어댑터를 통해 new-form 이라는 논리 뷰 이름을 획득한다.

2. ViewResolver 호출
    : new-form 이라는 뷰 이름으로 viewResolver를 순서대로 호출한다.
    - BeanNameViewResolver 는 new-form 이라는 이름의 스프링 빈으로 등록된 뷰를 찾아야 하는데 없다. InternalResourceViewResolver 가 호출된다.

3. InternalResourceViewResolver
    : 이 뷰 리졸버는 InternalResourceView 를 반환한다.
    
4. 뷰 - InternalResourceView
    : InternalResourceView 는 JSP처럼 포워드 forward() 를 호출해서 처리할 수 있는 경우에 사용한다. 
    
5. view.render()
    : view.render() 가 호출되고 InternalResourceView 는 forward() 를 사용해서 JSP를 실행한다.

- 참고
    - InternalResourceViewResolver 는 만약 JSTL 라이브러리가 있으면 InternalResourceView 를 상속받은 JstlView 를 반환
    - 다른 뷰는 실제 뷰를 렌더링하지만, JSP의 경우 forward() 통해서 해당 JSP로 이동(실행)해야 렌더링이 된다. JSP를 제외한 나머지 뷰 템플릿들은 forward() 과정 없이 바로 렌더링 된다

## 스프링 MVC - 시작하기

- 지금까지 만들었던 프레임워크에서 사용했던 컨트롤러를 @RequestMapping 기반의 스프링 MVC 컨트롤러 변경해보자.

* SpringMemberFormControllerV1 - 회원 등록 폼

 - @Controller
    - 스프링이 자동으로 스프링 빈으로 등록한다. (내부에 @Component 애노테이션이 있어서 컴포넌트 스캔의 대상이 됨)
    - 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다.

 - @RequestMapping 
    - 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다. 
    - 애노테이션을 기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다.

 - ModelAndView 
    - 모델과 뷰 정보를 담아서 반환하면 된다.

* SpringMemberSaveControllerV1 - 회원 저장
    
    - mv.addObject("member", member)
        - 스프링이 제공하는 ModelAndView 를 통해 Model 데이터를 추가할 때는 addObject() 를 사용하면 된다. 이 데이터는 이후 뷰를 렌더링 할 때 사용된다.

* SpringMemberListControllerV1 - 회원 목록



## 스프링 MVC - 컨트롤러 통합

- @RequestMapping 을 잘 보면 클래스 단위가 아니라 메서드 단위에 적용된 것을 확인할 수 있다. 따라서 컨트롤러 클래스를 유연하게 하나로 통합할 수 있다.
    - SpringMemberControllerV2

- 컨트롤러 클래스를 통합하는 것을 넘어서 조합도 가능하다.
    - 클래스 레벨에 다음과 같이 @RequestMapping 을 두면 메서드 레벨과 조합이 된다.
    - ex. 클래스 레벨 @RequestMapping("/springmvc/v2/members") + 메서드 레벨 @RequestMapping("/new-form") == /springmvc/v2/members/new-form

## 스프링 MVC - 실용적인 방식

- 실무에서는 주로 이 방법 사용

- SpringMemberControllerV3 생성

    * Model 파라미터
        - save() , members() 를 보면 Model을 파라미터로 받는 것을 확인할 수 있다. 스프링 MVC도 이런 편의 기능을 제공한다.
    
    * ViewName 직접 반환
        - 뷰의 논리 이름을 반환할 수 있다.

    * @RequestParam 사용
        - 스프링은 HTTP 요청 파라미터를 @RequestParam 으로 받을 수 있다. @RequestParam("username") 은 request.getParameter("username") 와 거의 같은 코드라 생각하면 된다. 물론 GET 쿼리 파라미터, POST Form 방식을 모두 지원한다.

    * @RequestMapping --> @GetMapping, @PostMapping
        - @RequestMapping 은 URL만 매칭하는 것이 아니라, HTTP Method도 함께 구분할 수 있다.
            - @RequestMapping(value = "/new-form", method = RequestMethod.GET)
                == @GetMapping("/new-form")
