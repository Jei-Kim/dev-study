## SECTION 6 _ 컴포넌트 스캔

### 컴포넌트 스캔과 의존관계 자동 주입 시작하기

- 스프링 빈이 수백개가 되면 일일이 등록하기 귀찮고 설정 정보가 커지며 누락이 생기는 문제점 등 발생
    - AutoAppConfig 클래스 생성, @ComponentScan 애노테이션 추가
        - 컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록
    - 클래스마다 @Component 추가

- 이전에 AppConfig에서는 @Bean 으로 직접 설정 정보를 작성, 의존관계도 직접 명시함
- 이제 설정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결해야 함
<br> -> @Autowired로 의존관계를 자동으로 주입
(ComponentScan을 쓰게 되면 수동으로 설정해 줄 수 있는 공간이 없어지기 때문에 자연스레 Autowired도 쓰게 됨)
<br> **** ac.getBean(MemberRepository.class)와 비슷하게 작동한다고 이해(자세한 설명은 다음 섹션에서 이어짐)*

### 탐색 위치와 기본 스캔 대상

* 탐색 위치

    - 탐색할 패키지의 시작 위치 지정
        - 모든 자바 클래스를 다 컴포넌트 스캔하면 시간이 오래 걸림 -> 필요한 위치부터 탐색하도록 시작
위치를 지정 -> @ComponentScan(basePackages = "hello.core",}

   - 권장하는 방법
      - 설정 정보 클래스의 위치를 프로젝트 최상단에 둠
        - com.hello(프로젝트 시작 루트)에 AppConfig 같은 메인 설정 정보를 두고 컴포넌트 스캔 애노테이션 추가하기
        - 위의 방법과 같은 basePackages 지정은 생략

- c.f. 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를 이 프로젝트 시작 루트 위치에 두는 것이 관례

* 컴포넌트 스캔 기본 대상

- 컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함
 - & 부가 기능 수행 (+로 표시)
    - @Component : 컴포넌트 스캔에서 사용 
    - @Controlller : 스프링 MVC 컨트롤러에서 사용 (+ 스프링 MVC 컨트롤러로 인식)
    - @Service : 스프링 비즈니스 로직에서 사용 (특별한 처리 x, 개발자들이 핵심 비즈니스 계층 인식하는데 도움)
    - @Repository : 스프링 데이터 접근 계층에서 사용 (+ 데이터 계층의 예외를 스프링 예외로 변환)
    - @Configuration : 스프링 설정 정보에서 사용 (스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리)

### 필터

- MyIncludeComponent, MyExcludeComponent 인터페이스 생성 후 @Component 내 애노테이션 추가
- ComponentFilterAppConfigTest 클래스 생성 후 테스트
    - includeFilters 에 MyIncludeComponent 애노테이션을 추가해서 BeanA가 스프링 빈에 등록
    - excludeFilters 에 MyExcludeComponent 애노테이션을 추가해서 BeanB는 스프링 빈에 등록되지 않음을 확인

- FilterType에는 ANNOTATION, ASSIGNABLE_TYPE, ASPECTJ, REGEX, CUSTOM의 5가지가 있음
    - 옵션을 변경하면서 사용하기 보다는 스프링의 기본 설정에 최대한 맞추어 사용하는 것을 권장

### 중복 등록과 충돌

* 자동 빈 등록 vs 자동 빈 등록
    - ConflictingBeanDefinitionException 예외 발생

* 수동 빈 등록 vs 자동 빈 등록
    - 수동 빈이 자동 빈을 오버라이딩, 수동 빈 등록이 우선권을 가짐
    - 현실적으로 개발자의 의도와 달리 이런 결과가 만들어지기 때문에, 최근 스프링부트에서는 이런 경우 오류가 발생하도록 기본값을 바꿈
    - CoreApplication 실행하면(스프링부트를 통해 실행) 오류가 발생하는 것을 확인할 수 있음
        - -> (Consider renaming one of the beans or enabling overriding by setting
        spring.main.allow-bean-definition-overriding=true)
        - -> 안내대로 application.properties에 true로 바꿔주는 코드 추가하면 위와 같이 수동 빈이 오버라이딩 됨 but 권장하지 않음