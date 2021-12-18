# 스프링 핵심원리_기본편

## 컴포넌트 스캔

1. 컴포넌트 스캔과 의존관계 자동 주입 시작하기

    - 지금까지는 @Configuration이 있는 클래스에 설정 정보에 등록할 스프링 빈을 나열함

    - 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔을 제공

    - 의존관계도 자동으로 주입하는 @Autowired 기능도 제공

    - 컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 찾아서 빈으로 등록

    - @ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록하는데, 이 때 스프링 빈의 기본 이름은 클래스 명을 사용하되 앞글자만 소문자

    - @Autowired를 지정하면 스프링 컨테이너가 파라미터의 타입으로 빈을 찾아서 주입한다


2. 탐색 위치와 기본 스캔 대상

    - 모든 자바 클래스를 다 검색하면 너무 오래 걸린다 (라이브러리까지도 탐색)

    - basePackages => 탐색 시작 위치
    - basePackageClasses = xxx.class => 지정한 클래스가 속해있는 패키지부터 하위 패키지를 모두 탐색
    - 지정안하면? (default) => @ComponenetScan이 있는 클래스의 패키지부터 하위 패키지를 모두 탐색

    * 지정하지 않고 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것이 좋다! (루트위치에 둬라!)

    - 컴포넌트 스캔 기본 대상
        - @Component : 컴포넌트 스캔에서 사용
        - @Controller : 스프링MVC 컨트롤러에서 사용
        - @Service : 스프링 비즈니스 로직에서 사용
        - Repository : 스프링 데이터 접근 계층에서 사용
        - @Configuration : 스프링 설정 정보에서 사용

    * 애노테이션에서는 상속관계라는 것이 없음 -> 애노테이션이 특정 애노테이션을 가지고 있는 것은 자바 기능이 아닌 스프링이 지원하는 기능

    * 애노테이션의 역할
        - @Controller : 스프링MVC 컨트롤러로 인식
        - Repository : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환
        - @Configuration : 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 처리
        - @Service : 별 기능 없음, 개발자들이 비즈니스 로직이 있겠거니 생각하게 해줌


3. 필터

    - includeFilters : 컴포넌트 스캔 대상을 추가로 지정
    - excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정

    - FilterType 5가지 옵션
        - ANNOTATION : 기본값, 애노테이션을 인식해서 동작
        - ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작
        - ASPECTJ : AspectJ 패턴 사용
        - REGEX : 정규 표현식
        - CUSTOM : TypeFilter이라는 인터페이스를 구현해서 처리

    * @Component면 충분하기 때문에, includeFilters를 사용할 일은 거의 없다 excludeFilters는 여러가지 이유로 간혹 사용한다 


4. 중복 등록과 충돌

    - 자동 빈 등록 vs 자동 빈 등록
        
        - ConflictingBeanDefinitionException 예외 발생

    - 수동 빈 등록 vs 자동 빈 등록

        - 수동 빈 등록이 우선권을 가짐

        - 최근 스프링 부트에서는 오류가 나도록 기본 값을 바꿈

            - Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true