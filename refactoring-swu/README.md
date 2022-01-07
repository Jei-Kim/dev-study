# 오류 및 해결

## controller의 loginUser 변수 jsp에서 인식 x 
-> request.setAttribute 추가

## Autowired 애노테이션 충돌 
-> 구조 정리

## service메서드의 리턴값이 repo와 일치 x
-> 파라미터는 넘어오나 로그인 실패 -> 메서드명 재확인 및 수정 -> 로그인 성공

## application.properties 설정 추가

## mapper.xml의 경로 재설정

## BindingResult result 오류 -> modelattribute랑 와야하는ㅡㄷㅅ?

# study domain @Data 컴파일 오류

Generating equals/hashCode implementation but without a call to superclass, even though this class does not extend java.lang.Object. If this is intentional, add '@EqualsAndHashCode(callSuper=false)' to your type.

- ->  객체의 직접적인 서브클래스가 아닌 경우, Super 클래스를 호출하기 때문에 생기는 오류

* 해결?
    -  @Data는 constructor, getter, setter, toString, equals, hashcode 등 메서드를 자동으로 생성 -> 이때 만들어진 equals와 hashcode 메소드가 부모 클래스 필드까지 고려할지 안 할지를 설정할 수 있다.

    - callSuper = true: 부모클래스 필드 값도 동일한지 체크

    - callSuper = false: 본인클래스 필드 값만 고려

 

# 공부

## 



# ?

- service 메서드 public ?
- 애노테이션 정리
- mypage ?
- pageTitle 설정 알아보기










