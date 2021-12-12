# 스프링 핵심원리_기본편

## 스프링 컨테이너와 스프링 빈

1. 스프링 컨테이너 생성

    - ApplicationContext를 스프링 컨테이너라 한다

    - ApplicationContext는 인터페이스이다

    - 스프링 컨테이너는 XML 기반으로 만들 수 있고, 애노테이션 기반의 자바 설정 클래스로도 만들 수 있다

* 스프링 컨테이너의 생성 과정

    - 1. 스프링 컨테이너 생성 (스프링 빈 저장소 생성)

        - new AnnotationConfigApplicationContext(AppConfig.class)

            - 스프링 컨테이너를 생성할 때는 구성 정보를 지정해주어야 함 (여기서는 AppConfig.class를 구성 정보로 지정)


    - 2. 스프링 빈 저장소에 스프링 빈 등록

        - 스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보(AppConfig.class)를 사용해서 스프링 빈을 등록

        - 빈 이름은 @Bean 애노테이션이 붙은 메서드의 이름을 사용

            - 빈 이름을 직접 부여할 수 도 있다 ( @Bean(name="abcd") )

        * 주의 : 빈 이름은 항상 다른 이름을 부여해야 한다

            - 같은 이름을 부여하면, 다른 빈이 무시되거나, 기존 빈을 덮어버리거나 설정에 따라 오류가 발생한다

    - 3. 스프링 빈 의존관계 설정

        - 스프링 컨테이너는 설정 정보(AppConfig.class)를 참고해서 의존관계를 주입(DI)한다.


2. 컨테이너에 등록된 모든 빈 조회

    * ac = 스프링 컨테이너 객체

    - ac.getBeanDefinitionNames() => 스프링에 등록된 모든 빈 이름을 조회
    
    - ac.getBean() => 빈 이름으로 빈 객체를 조회

    - 스프링이 내부에서 사용하는 빈은 getRole()로 구분 가능
         - beanDefinition.getRole()

         - ROLE_APPLICATION : 일반적으로 사용자가 정의한 빈
         - ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈


3. 스프링 빈 조회

    - 스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적인 조회 방법

        - ac.getBean(빈이름, 타입)
            ex) MemberService memberService = ac.getBean("memberService", MemberService.class);

        - ac.getBean(타입)
            ex) MemberService memberService = ac.getBean(MemberService.class);

        - ac.getBean(구체 타입)
                ex) MemberService memberService = ac.getBean(MemberServiceImpl.class);

            * 구체 타입으로 조회하면 변경 시 유연성이 떨어지므로 지양

        * 조회 대상 스프링 빈이 없으면 예외발생
            ex) NoSuchBeanDefinitionException : No bean named 'abcd' available


4. 스프링 빈 조회 - 동일한 타입이 둘 이상

    - 타입으로 조회 시 (ac.getBean(타입) 사용 시) 같은 타입의 스프링 빈이 둘 이상이면 오류 발생 !
        -> NoUniqueBeanDefinitionException 발생
            -> 빈 이름을 지정 (ac.getBean(빈이름, 타입) 사용)

    - ac.getBeansOfType()을 사용하면 해당 타입의 모든 빈을 조회할 수 있다


5. 스프링 빈 조회 - 상속관계

    - 부모 타입으로 조회하면, 자식 타입도 함께 조회한다.

        -> Object 타입으로 조회하면 모든 스프링 빈을 조회한다.


6. BeanFactory와 ApplicationContext

                    BeanFactory (Interface)
                        ↑
                ApplicationContext (Interface)
                        ↑
                AnnotionConfigApplicationContext

    - BeanFactory

        - 스프링 컨테이너의 최상위 인터페이스

        - 스프링 빈을 관리하고 조회하는 역할을 담당

        - getBean() 제공

    - ApplicationContext

        - BeanFactory 기능을 모두 상속받아서 제공 + 편리한 부가 기능을 제공

        - 애플리케이션을 개발할 때는 BeanFactory가 제공하는 기능 뿐만 아니라 수 많은 기능이 필요

            - ApplicationContext가 제공하는 부가기능

                - 메시지소스를 활용한 국제화 기능
                    - 한국에서 들어오면 한국어, 영어권에서 들어오면 영어로 출력

                - 환경변수
                    - 로컬, 개발, 운영 등을 구분해서 처리
                
                - 애플리케이션 이벤트
                    - 이벤트를 발행하고 구독하는 모델을 편리하게 지원

                - 편리한 리소스 조회
                    - 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회


7. 다양한 설정 형식 지원 - 자바코드, XML

    - 스프링 컨테이너는 다양한 형식의 설정 정보를 받아들일 수 있게 유연하게 설계
        - 자바 코드, XML, Groovy 등등

                    BeanFactory
                        ↑
                ApplicationContext
                        ↑
                AnnotationConfigApplicationContext (-> AppConfig.class)
                        or
                GenericXmlApplicationContext (-> appConfig.xml)
                        or
                XxxApplicationContext (-> AppConfig.xxx)

    - 애노테이션 기반 자바 코드 설정 사용

        - AnnotationConfigApplicationContext 클래스를 사용하면서 자바 코드로 된 설정 정보를 넘기면 된다
            
            ex) new AnnotationConfigApplicationContext(AppConfig.class)

    - XML 설정 사용

        - 컴파일 없이 빈 설정 정보를 변경할 수 있는 장점이 있음

        - GenericXmlApplicationContext를 사용하면서 xml 설정 파일을 넘기면 된다.

            ex) ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml")

        - 더 필요하면 스프링 공식 레퍼런스 문서 확인
        https://spring.io/projects/spring-framework


8. 스프링 빈 설정 메타 정보 - BeanDefinition

        - BeanDefinition을 통해 다양한 설정 형식 지원 

        - 스프링 컨테이너는 자바 코드인지, XML인지 몰라도 된다. 오직 BeanDefinition만 알면 된다

        - BeanDefinition을 빈 설정 메타정보라 한다
            - @Bean, <bean> 하나 당 각각의 메타 정보가 생성된다

        - 스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성한다

                스프링 컨테이너 ---> BeanDefinition
                                      ↑
                                AppConfig.class
                                      or
                                appConfig.xml
                                      or
                                appConfig.xxx


            - AnnotationConfigApplicationContext는 AnnotatedBeanDefinitionReader를 사용해서 AppConfig.class를 읽고 BeanDefinition을 생성한다.

            - GeneficXmlApplicationContext는 XmlBeanDefinitionReader를 사용해서 appConfig.xml 설정 정보를 읽고 BeanDefinition을 생성한다

        - BeanDefinition 정보

            - BeanClassName: 생성할 빈의 클래스 명(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음) 
            
            - factoryBeanName: 팩토리 역할의 빈을 사용할 경우 이름, 예) appConfig 
            
            - factoryMethodName: 빈을 생성할 팩토리 메서드 지정, 예) memberService
            
            - Scope: 싱글톤(기본값)
            
            - lazyInit: 스프링 컨테이너를 생성할 때 빈을 생성하는 것이 아니라, 실제 빈을 사용할 때 까지 최대한 생성을 지연처리 하는지 여부
            
            - InitMethodName: 빈을 생성하고, 의존관계를 적용한 뒤에 호출되는 초기화 메서드 명 
            
            - DestroyMethodName: 빈의 생명주기가 끝나서 제거하기 직전에 호출되는 메서드 명 
            
            - Constructor arguments, Properties: 의존관계 주입에서 사용한다. (자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)

    - 정리

        - 1. 스프링 컨테이너 생성 (스프링 빈 저장소 생성)

        - 2. BeanDefinitionReader가 파라미터로 넘어온 설정 정보를 읽고 BeanDefinition을 생성 
        
        - 3. 스프링 빈 저장소에 스프링 빈 등록

        - 4. 스프링 빈 의존관계 설정
