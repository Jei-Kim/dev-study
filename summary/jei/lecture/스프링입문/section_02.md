## SECTION 02 _ 스프링 웹 개발 기초

### 정적 컨텐츠

- 스프링 부트 정적 컨텐츠 기능
    - hello-static.html

### MVC와 템플릿 엔진

- MVC: Model, View, Controller

    - 웹브라우저에서 내장 톰캣 서버로 localhost:8080/hello-mvc 요청
    - 톰캣 서버가 스프링 부트 내 스프링 컨테이너 내 helloController 실행(mapping값 확인)
    - helloController가 hello-template 리턴, model(name:spring)
    - ViewResolver가 templates/hello-template.html(Thymeleaf 템플릿 엔진 처리) 웹 브라우저로 보냄 (html 변환 후)

### API

* @ResponseBody 문자 반환
    - ResponseBody 를 사용하면 뷰 리졸버( viewResolver )를 사용하지 않음
    - 대신에 HTTP의 BODY에 문자 내용을 직접 반환(HTML BODY TAG를 말하는 것이 아님)
        - ex. return "hello" + name일 때, http://localhost:8080/hello-string?name=spring실행 -> hello-spring 출력됨

* @ResponseBody 객체 반환
    - @ResponseBody 를 사용하고, 객체를 반환하면 객체가 JSON으로 변환됨


* 정리
 - @ResponseBody 를 사용
    - -> HTTP의 BODY에 문자 내용을 직접 반환
    - -> viewResolver 대신에 HttpMessageConverter 가 동작

****자세한 내용은 스프링 MVC강의에서 설명*