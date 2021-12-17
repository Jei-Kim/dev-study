## SECTION 05 _ 회원관리 예제 - 웹 MVC 개발

### 회원 웹 기능 - 홈 화면 추가

- HomeController 클래스 생성
    ( @GetMapping("/") )
- 홈 gtml 생성
- c.f. 컨트롤러가 정적 파일보다 우선순위가 높다.

### 회원 웹 기능 - 등록

* 회원 등록 폼 개발
    - MemberController(회원 등록 폼 컨트롤러) 클래스 생성
    - 회원 등록 폼 html 생성
    ( resources/templates/members/createMemberForm )

* 회원 등록 컨트롤러
    - MemberForm(웹 등록 화면에서 데이터를 전달 받을 폼 객체)
    - 회원 컨트롤러에 회원을 실제 등록하는 기능 추가

### 회원 웹 기능 - 조회

    - 회원 컨트롤러에 조회 기능 추가
    - 회원 리스트 html 생성