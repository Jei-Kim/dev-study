# 모든 개발자를 위한 HTTP 웹 기본 지식

## HTTP 프로토콜 - URI와 웹 브라우저 요청 흐름

1. URI 

    - URI :  Uniform Resource Identifier
        - Uniform → 리소스 식별하는 통일된 방식
        - Resource → 자원, URI로 식별할 수 있는 모든 것(제한 없음)
        - Identifier → 다른 항목과 구분하는데 필요한 정보
        
    - URI = URL과 URN을 포함
        - URL : Uniform Resource Locator → 리소스가 있는 위치를 지정
            - foo:// example.com:8042/over/there?name=ferret#nose
                
                foo : sheme
                
                [example.com:8042](http://example.com:8042) : authrity
                
                /over/there : path
                
                ?name=ferret : query
                
                #nose : fragment
                
        - URN : Uniform Resource Name → 리소스에 이름을 부여
            - urn : example : animal : ferret : nose
   
                urn : sheme
                
                example ~ nose : path
                
    - 문법
        
        ex) https://www.google.com:443/search?q=hello&hl=ko
        
        - https - 프로토콜
            - 프로토콜
                - 어떤 방식으로 자원에 접근할 것인가 하는 약속 규칙 예) http, https, ftp 등등
                - http는 80포트, https는 443 포트를 주로 사용, 포트는 생략 가능
                - https 는 http에 보안 추가 (HTTP Secure)
      
        - [www.google.com](http://www.google.com) - 호스트명
            - 호스트명
            - 도메인명 또는 IP주소를 직접 사용가능
      
        - 443 - 포트 번호
            - 포트
            - 접속 포트
            - 일반적으로 생략, 생략시 http는 80, https는 443
      
        - /search : path
            - 리소스 경로
            - 계층적 구조
      
        - q=hello&hl=ko : 쿼리 파라미터
            - key = value 형태
            - ?로 시작, &로 추가 가능
            - query parameter, query string 등으로 불림, 웹서버에 제공하는 파라미터, 문자 형태
      
        - fragment
            - html 내부 북마크 등에 사용
            - 서버에 전송하는 정보 아님

2. 웹 브라우저 요청 흐름

    1) 웹브라우저가 DNS에서 ip와 포트번호를 조회

    2) 찾은  ip와 포트번호로 구글서버와 3 way handshake를 통해 연결

    3) 데이터를 전달

    4) TCP.IP 패킷 생성해서 데이터를 감싼다, HTTP 메시지 포함

    5) 전송