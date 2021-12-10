## SECTION 3 _ 스프링 핵심 원리 이해2 - 객체 지향 원리 적용 


### 새로운 할인 정책 개발

- 개발 도중 기획자가 요구사항을 변경, 추가할 경우에 대한 강의
    ex. 기존의 정액 할인에서 정률 할인으로 정책을 변경할 경우
    *** 애자일: 규칙을 따르기보다는 변화에 빠르게 대응하라

- discountPolicy의 구현체 RateDiscountPolicy 클래스 생성
- test > discount 패키지 생성, 하위에 RateDiscountPolicyTest 클래스 생성하여 JUnit 테스트 수행
    *** 인텔리제이에서는 테스트 클래스를 자동으로 생성해주는 기능이 있음, 이클립스는 수동으로 만들어 줄 것
    *** 테스트 클래스 작성 시 중요한 점: 성공할 경우 & 실패할 경우의 메서드를 모두 작성해서 테스트해보기


### 새로운 할인 정책 적용과 문제점

- 새로운 할인 정책을 애플리케이션에 적용
    - 클라이언트인 OrderServiceImpl 코드 수정
        private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
        -> private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

- 문제점 발견(pdf 그림 참고)
    - 클래스 의존관계를 분석해 보자. 추상(인터페이스) 뿐만 아니라 구체(구현) 클래스에도 의존하고 있다.
    - 추상(인터페이스) 의존: DiscountPolicy
    - 구체(구현) 클래스: FixDiscountPolicy , RateDiscountPolicy
    ---> DIP(구체에 의존하지말고 추상에만 의존하라) 위반, OCP(변경하지 않고 확장 가능) 위반

- 문제점 해결
    - 해결책 모색은 어떻게 구체에 의존하지 않을 수 있는가?에서 출발
    - 인터페이스에만 의존하도록 설계를 변경
        - 클라이언트인 OrderServiceImpl 코드 수정
            -  private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
                -> private DiscountPolicy discountPolicy;
    ---> but 구현체가 없어서 NPE(null pointer exception)발생
    ----> 해결방안? 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 함
            (다음 강의에 이어짐)


### 관심사의 분리

- 이해를 위한 예시
    - 애플리케이션 == 공연 / 인터페이스 == 배역 / 구현체 == 배우라고 가정
    - 배우가 상대배역을 맡을 배우를 캐스팅하지 않음. 그러나 이전 코드는 인터페이스가 구현체를 선택함
        -> 다양한 책임을 가지고 있음(단점)

- 해결법: 관심사를 분리하자
    - 배우는 본인의 역할인 배역을 수행하는 것에만 집중해야 한다.
    - 배우는 어떤 상대역이 선택되더라도 똑같이 공연을 할 수 있어야 한다.
    - 공연을 구성하고, 담당 배우를 섭외하고, 역할에 맞는 배우를 지정하는 책임을 담당하는 **별도의 공연 기획자**가 나올 시점이다.
    - 공연 기획자를 만들고, 배우와 공연 기획자의 책임을 확실히 분리

- **별도의 공연 기획자** 역할을 하는 AppConfig의 등장
    : 애플리케이션의 전체 동작 방식을 구성(config)하기 위해 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스

    - AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
        - MemberServiceImpl
        - MemoryMemberRepository
        - OrderServiceImpl
        - FixDiscountPolicy

    - AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)
        - MemberServiceImpl & MemoryMemberRepository
        - OrderServiceImpl & MemoryMemberRepository, FixDiscountPolicy
    *** 생성자 없어서 컴파일 오류 발생, 각각의 구현 클래스에서 생성자를 주입해주어야 함

    - 설계 변경으로 MemberServiceImpl 은 MemoryMemberRepository 를 의존하지 않음
    - 단지 MemberRepository 인터페이스만 의존함
    - MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없음
    - MemberServiceImpl의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부( AppConfig )에서 결정됨
    - MemberServiceImpl 은 이제부터 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중하면 된다.

    *** OrderServiceImpl도 상동

    **DI(Dependency Injection; 의존성 주입)**
       - appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl 을 생성하면서 생성자로 전달한다.
        - 클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서 DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라 한다.


### AppConfig 리팩터링

- 현재 AppConfig 코드에서는 구현과 역할을 한 눈에 알아볼 수 없음 
    ex. memberRepository 라는 역할은 보이지 않음
    -> 중복을 제거하고, 역할에 따른 구현이 보이도록 리팩터링 하자
        (구성&설정을 담당하는 AppConfig에서는 역할과 구현 클래스가 한눈에 들어와야 함)


### 새로운 구조와 할인 정책 적용

- AppConfig 에서 할인 정책 역할을 담당하는 구현을 FixDiscountPolicy RateDiscountPolicy 객체로 변경
    - 이제 할인 정책을 변경해도, 애플리케이션의 구성 역할을 담당하는 AppConfig만 변경하면 됨
    - 클라이언트 코드인 OrderServiceImpl 를 포함해서 사용 영역의 어떤 코드도 변경할 필요가 없음
    - 구성 영역은 당연히 변경된다. 구성 역할을 담당하는 AppConfig를 애플리케이션이라는 공연의 기획자로 생각하자. 공연 기획자는 공연 참여자인 구현 객체들을 모두 알아야 한다.


### 전체 흐름 정리

- 새로운 할인 정책 개발 -> 새로운 할인 정책 적용과 문제점 관심사의 분리 -> AppConfig 리팩터링 -> 새로운 구조와 할인 정책 적용
    *** 위의 각 단계 세부설명을 통해 어떤 변화가 있었는지 복습할 것(AppConfig의 등장과 그 역할에 집중해서)


### 좋은 객체 지향 설계의 5가지 원칙의 적용

- 이전 강의에서 학습한 '좋은 객체 지향 설계의 5가지 원칙'이 이번 섹션에서 어떻게 활용되었는지 학습

* SRP(한 클래스는 하나의 책임만)
    - 기존에 클라이언트 객체가 직접 구현 객체를 생성, 연결, 실행하는 다양한 책임을 수행했던 문제점을 해결하기 위해 관심사를 분리함
    - AppConfig 클래스 생성하여 구현 객체를 생성, 연결하는 역할 수행 / 클라이언트는 실행하는 책임만을 담당함

* DIP(추상화에 의존해야지, 구체화에 의존하면 안 된다 -> 의존성 주입 활용)
    - 기존 클라이언트 코드( OrderServiceImpl )는 DIP를 지키며 DiscountPolicy 추상화와 FixDiscountPolicy 구체화 구현 클래스에 동시에 의존
        -> 변경 시 클라이언트 코드도 함께 변경해야 하는 문제 발생

    **해결**
     클라이언트 코드가 DiscountPolicy 추상화 인터페이스에만 의존하도록 코드를 변경 후, AppConfig가 FixDiscountPolicy 객체 인스턴스를 클라이언트 코드 대신 생성해서 클라이언트 코드에 의존관계를 주입

* OCP(확장에는 열리고 변경에는 닫혀야 함)가 적용되었음
    - AppConfig 생성 및 리팩터링으로 변경 시 사용 영역의 어떤 코드도 변경할 필요가 없었고, 구성 영역만 변경하면 되도록 구현


### IoC, DI, 그리고 컨테이너

* 제어의 역전 IoC(Inversion of Control)
    - 내가 직접 호출하는 것이 아니라, 프레임워크와 같은 외부에서 호출을 대신해 줌
    - 스프링에만 국한되는 개념이 아님! 개발 관련해서 자주 나오는 중요한 개념
    
    * 프레임워크 vs 라이브러리
        - 프레임워크는 내가 작성한 코드를 제어하고, 대신 실행함 ex. JUnit
        - 라이브러리는 내가 작성한 코드가 직접 제어의 흐름을 담당함 ex. java코드를 xml이나 json으로 변경하는 경우

* 의존관계 주입 DI(Dependency Injection)
    - 의존관계는 정적인 클래스 의존 관계와, 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계 둘을 분리해서 생각해야 함
    - 정적인 클래스 의존관계는 애플리케이션을 실행하지 않아도 분석할 수 있음
    - 동적인 갹체 의존관계는 애플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존 관계임

    - 애플리케이션 실행 시점(런타임)에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제 의존관계가 연결 되는 것을 의존관계 주입이라 함
        -> 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.

* IoC 컨테이너, DI 컨테이너
    - AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것
    - 의존관계 주입에 초점을 맞추어 최근에는 주로 DI 컨테이너라 함(어샘블러, 오브젝트 팩토리라고 불리기도 함)
    - 스프링이 DI 컨테이너 역할을 하는 것은 맞지만, DI 컨테이너가 반드시 스프링을 뜻하는 것은 아님!


### 스프링으로 전환하기

- AppConfig 스프링 기반으로 변경
    - @Configuration 애노테이션으로 AppConfig가 설정을 구성하는 클래스임을 명시
    - 각 메서드에 @Bean 을 붙여 스프링 컨테이너에 스프링 빈으로 등록
- MemberApp에 스프링 컨테이너 적용, OrderApp에 스프링 컨테이너 적용

    **스프링 컨테이너**

    - ApplicationContext 를 스프링 컨테이너라 함
    - 기존(이전 강의까지)에는 개발자가 AppConfig 를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제 스프링 컨테이너를 통해서 사용할 수 있음

    - 스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용
        - 여기서 @Bean 이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록 -> 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다.
    
    - 스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다. ( memberService , orderService )
        -> @Bean(name:"aa") 이런 식으로 변경 가능하지만, 관례는 메서드 이름을 따르는 것
        
    - 기존에는 개발자가 필요한 객체를 AppConfig 를 사용해서 직접 조회 but 이제 스프링 컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 함. 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾음
    - 기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제 스프링 컨테이너에 객체를 스프링 빈으로 등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경됨
    
    ***스프링 컨테이너를 사용하면 어떤 장점이 있는지는 다음 강의를 통해 알아 볼 예정
