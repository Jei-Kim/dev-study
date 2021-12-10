## SECTION 4 _ 스프링 컨테이너와 스프링 빈


### 스프링 컨테이너 생성

***

ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

***

- ApplicationContext
    - 스프링 컨테이너, 인터페이스
    - 스프링 컨테이너는 XML기반 혹은 애노테이션 기반의 자바 설정 클래스로 만들 수 있음
    - 이전 강의에서 AppConfig를 사용했던 방식이 애노테이션 기반의 자바 설정 클래스 스프링 컨테이너를 만든 것

- 자바 설정 클래스를 기반으로 스프링 컨테이너(ApplicationContext) 생성
    - new AnnotationConfigApplicationContext(AppConfig.class);
        - 이 클래스는 ApplicationContext 인터페이스의 구현체

*** 정확히는 스프링 컨테이너를 부를 때 BeanFactory, ApplicationContext 로 구분 -> 다음 강의에 이어짐 
    BeanFactory를 직접 사용하는 경우는 거의 없으므로 일반적으로 ApplicationContext 를 스프링 컨테이너라 부름

* 스프링 컨테이너 생성 과정

    1. 스프링 컨테이너 생성
        - new AnnotationConfigApplicationContext(AppConfig.class)
            - 스프링 컨테이너를 생성할 때는 구성 정보를 지정해주어야 함
            - 여기서는 AppConfig.class 를 구성 정보로 지정함

    2. 스프링 빈 등록
        - AppConfig 클래스 메서드에 @Bean 애노테이션 추가
            - 스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보를 사용해서 스프링 빈을 등록
            *** 빈 이름은 메서드 이름을 기본으로 사용, 빈 이름을 직접 부여할 수도 있음. but 관례상 기본으로 사용
        
    3. 스프링 빈 의존관계 설정 - 준비
        - 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입(DI)함
        *** 싱글톤 컨테이너에서 설명 예정

### 컨테이너에 등록된 모든 빈 조회

- ApplicationContextInfoTest 클래스 생성 -> 등록된 빈 조회
    * 모든 빈 출력하기
         - ac.getBeanDefinitionNames() : 스프링에 등록된 모든 빈 이름을 조회
         - ac.getBean() : 빈 이름으로 빈 객체(인스턴스)를 조회한다.
    * 애플리케이션 빈 출력하기
    : 스프링 내부 빈 제외하고 내가 등록한 빈만 출력 -> 스프링이 내부에서 사용하는 빈은 getRole() 로 구분
    - ROLE_APPLICATION : 일반적으로 사용자가 정의한 빈
    - ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈

### 스프링 빈 조회 - 기본

- ApplicationContextBasicFindTest
    - ac.getBean(빈이름, 타입)
    - ac.getBean(타입)

c.f. 조회 대상 스프링 빈이 없으면 예외 발생
    NoSuchBeanDefinitionException: No bean named 'xxxxx' available

### 스프링 빈 조회 - 동일한 타입이 둘 이상

- ApplicationContextSameBeanFindTest
    - 타입으로 조회시 같은 타입의 스프링 빈이 둘 이상이면 오류가 발생 -> 빈 이름 지정으로 해결
    - ac.getBeansOfType() 을 사용하면 해당 타입의 모든 빈을 조회함

### 스프링 빈 조회 - 상속 관계

- ApplicationContextExtendsFindTest

### BeanFactory와 ApplicationContext

* BeanFactory
    - 스프링 컨테이너의 최상위 인터페이스
    - 스프링 빈을 관리하고 조회하는 역할을 담당
    - getBean() 을 제공

* ApplicationContext
    - BeanFactory 기능을 모두 상속받아서 제공함
    - 빈을 관리하고 검색하는 기능을 하는데, BeanFactory와의 차이는? 
        - 애플리케이션을 개발할 때는 빈은 관리하고 조회하는 기능은 물론이고, 수 많은 부가기능이 필요

    * ApplicationContext 부가기능
        - 메시지소스를 활용한 국제화 기능: 접근 위치에 따라 그에 알맞는 언어 제공
        - 애플리케이션 이벤트: 이벤트를 발행하고 구독하는 모델을 편리하게 지원
        - 편리한 리소스 조회: 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회

-> BeanFactory를 직접 사용할 일은 거의 없다. 부가기능이 포함된 ApplicationContext를 사용
-> BeanFactory나 ApplicationContext를 스프링 컨테이너라 함

### 다양한 설정 형식 지원 - 자바 코드, XML

- 스프링 컨테이너는 다양한 형식의 설정 정보를 받아드릴 수 있게 유연하게 설계되어 있음
    -> 자바 코드, XML, Groovy 등등

* 애노테이션 기반 자바 코드 설정 사용
    - 강의에서 계속 진행되었던 설정 방법
    - new AnnotationConfigApplicationContext(AppConfig.class)
    - AnnotationConfigApplicationContext 클래스를 사용하면서 자바 코드로된 설정 정보를 넘기면 됨

* XML 설정 사용
    - GenericXmlApplicationContext 를 사용하면서 xml 설정 파일을 넘기는 방식

### 스프링 빈 설정 메타 정보 -BeanDefinition

- 스프링이 다양한 설정 형식을 지원할 수 있는 이유는 BeanDefinition 추상화
- 역할과 구현을 개념적으로 나눈 것
    - XML을 읽어서 BeanDefinition을 만들거나 자바 코드를 읽어서 BeanDefinition을 만들면 됨
    - 스프링 컨테이너는 자바 코드인지, XML인지 몰라도 BeanDefinition만 알면 됨

- BeanDefinition 을 빈 설정 메타정보라 함
- @Bean , <bean> 당 각각 하나씩 메타 정보가 생성
    - 스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성

- AnnotationConfigApplicationContext 는 AnnotatedBeanDefinitionReader 를 사용해서 AppConfig.class 를 읽고 BeanDefinition 을 생성
- GenericXmlApplicationContext 는 XmlBeanDefinitionReader 를 사용해서 appConfig.xml 설정 정보를 읽고 BeanDefinition 을 생성

-> 스프링이 다양한 형태의 설정 정보를 BeanDefinition으로 추상화해서 사용하는 것 정도만 이해하기!