# SECTION 02. 서블릿

## 프로젝트 생성

- 스프링 프로젝트 생성
    *JSP 실행 환경 위해서 WAR 선택*

- 롬복 적용

- Postman 설치

## HttpServletRequest - 개요

- 서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱
    - ->  결과를 HttpServletRequest 객체에 담아서 제공

* 임시 저장소 기능
: 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능

    - 저장: request.setAttribute(name, value)
    - 조회: request.getAttribute(name)

* 세션 관리 기능

- request.getSession(create: true)

## HttpServletRequest - 기본 사용법

- hello.servlet.basic.request.RequestHeaderServlet

## HTTP 요청 데이터 - 개요

- HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법

* 주로 사용하는 3가지 방법

    * GET - 쿼리 파라미터
        - /url?username=hello&age=20
        - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달 
        - 예) 검색, 필터, 페이징등에서 많이 사용하는 방식

    * POST - HTML Form
        - content-type: application/x-www-form-urlencoded
        - 메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20 
        - 예) 회원 가입, 상품 주문, HTML Form 사용

    * HTTP message body에 데이터를 직접 담아서 요청 
        - HTTP API에서 주로 사용, JSON, XML, TEXT
        - 데이터 형식은 주로 JSON 사용 
            - POST, PUT, PATCH

## HTTP 요청 데이터 - GET 쿼리 파라미터

- 메시지 바디 없이, URL의 쿼리 파라미터를 사용해서 데이터를 전달하자.
    - 예) 검색, 필터, 페이징등에서 많이 사용하는 방식

- 쿼리 파라미터는 URL에 다음과 같이 ?를 시작으로 보낼 수 있다. 추가 파라미터는 &로 구분하면 된다.
    - http://localhost:8080/request-param?username=hello&age=20

- 서버에서는 HttpServletRequest 가 제공하는 다음 메서드를 통해 쿼리 파라미터를 편리하게 조회할 수 있다.
    - request.getParameter("username"); //단일 파라미터 조회
    - request.getParameterNames(); //파라미터 이름들 모두 조회
    - request.getParameterMap(); //파라미터를 Map 으로 조회
    - request.getParameterValues("username"); //복수 파라미터 조회
        - 값이 중복일 때, 첫 번째 값 반환

## HTTP 요청 데이터 - POST HTML Form

- 이번에는 HTML의 Form을 사용해서 클라이언트에서 서버로 데이터를 전송해보자.
주로 회원 가입, 상품 주문 등에서 사용하는 방식이다.
    \- content-type: application/x-www-form-urlencoded
    \- 메시지 바디에 쿼리 파리미터 형식으로 데이터를 전달한다. username=hello&age=20

- 클라이언트(웹 브라우저) 입장에서는 두 방식에 차이가 있지만, 서버 입장에서는 둘의 형식이 동일하므로, request.getParameter() 로 편리하게 구분없이 조회할 수 있다.
- => request.getParameter() 는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원한다.

- GET URL 쿼리 파라미터 형식은 메시지 바디를 사용하지 않기 때문에 content-type이 없다.
- POST HTML Form 형식으로 데이터를 전달하면 HTTP 메시지 바디에 해당 데이터를 포함해서 보내기 때문에 content-type을 꼭 지정해야 한다.

## HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트

-  HTTP message body에 데이터를 직접 담아서 요청
    - HTTP API에서 주로 사용, JSON, XML, TEXT 
    - 데이터 형식은 주로 JSON 사용
    - POST, PUT, PATCH
- HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.
- inputStream은 byte 코드를 반환한다. byte 코드를 우리가 읽을 수 있는 문자(String)로 보려면 문자표
(Charset)를 지정해주어야 한다. 여기서는 UTF_8 Charset을 지정해주었다.

## HTTP 요청 데이터 - API 메시지 바디 - JSON

- JSON 형식 파싱 추가
    - JSON 형식으로 파싱할 수 있게 객체를 하나 생성하자
    - hello.servlet.basic.HelloData

## HttpServletResponse - 기본 사용법

* 역할
- HTTP 응답 메시지 생성 
    - HTTP 응답코드 지정 
    - 헤더 생성
    - 바디 생성

    * 편의 기능 제공
    - Content-Type, 쿠키, Redirect

## HTTP 응답 데이터 - 단순 텍스트, HTML

- 단순 텍스트 응답
    - 앞에서 살펴봄 ( writer.println("ok"); )
- HTML 응답
- HTTP API - MessageBody JSON 응답

## HTTP 응답 데이터 - API JSON

- HTTP 응답으로 JSON을 반환할 때는 content-type을 application/json 로 지정해야 한다. 
- Jackson 라이브러리가 제공하는 objectMapper.writeValueAsString() 를 사용하면 객체를 JSON 문자로 변경할 수 있다.
    -  application/json 은 스펙상 utf-8 형식을 사용하도록 정의되어 있다. 그래서 스펙에서 charset=utf-8과 같은 추가 파라미터를 지원하지 않음.
