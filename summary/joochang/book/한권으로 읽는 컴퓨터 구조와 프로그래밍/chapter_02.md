# Chapter02 - 전자 회로의 조합 논리

## 아날로그와 디지털의 차이

    - 계산자와 손가락은 1부터 10까지를 다룰 수 있지만 자와 달리 손가락은 1.1 등의 수를 표현할 수 없다.

    - 계산자는 연속적(실수를 표현할 수 있음)이며, 손가락은 이산적(하나하나 다른 존재로 구분된다는 뜻)이며 정수만 표현 가능

    - 전자기술에 대해 이야기할 때 아날로그는 연속적인 것을 뜻하며, 디지털은 이산적인 것을 뜻한다.

## 하드웨어에서 크기가 중요한 이유

    - 전기는 빛의 속도로 움직이며 이 물리적인 한계를 뛰어넘는 방법은 아직 존재하지 않는다.

    - 컴퓨터에서 전자를 움직일 때 전자의 이동 시간을 최소화하는 방법은 부품을 가능한 한 가깝게 위치시키는 방법밖에 없다.

    - 하드웨어를 작게 만들면 이동 거리가 줄어들고 에너지 양도 줄어든다.

    - 하지만 작게 만들다 보니 여러가지 문제가 발생

## 디지털을 사용하면 더 안정적인 장치를 만들 수 있다.

    - 하드웨어를 작게 만들면 속도와 효율은 좋아지지만, 물체가 너무 작아지면 서로 간섭하기 아주 쉬워진다.

    - 아날로그 장치는 값을 읽으려면 조금의 오차도 있으면 안되지만 이산적인 장치는 판정 기준이 있기 때문에 덜 까다롭다.

    - 전자기력은 중력과 마찬가지로 멀리 떨어진 물체에 영향을 끼칠 수 있다.

    - 이런 영향은 라디오와 같은 좋은 영향도 미치지만, 칩 안에서 선을 통하는 신호가 다른 선에 영향을 끼칠 수 있다는 뜻이기도 하다.

    - 현대 컴퓨터 칩 안의 선은 서로 몇 나노미터 떨어져 있고 현대 CPU에서 신호 간섭은 마치 도로에서 마주 보는 두 차가 서로 스쳐 지나갈 때 느껴지는 바람과 같다.

    - 이런 누화 효과를 방지할 적절한 방법이 없기 때문에, 더 높은 판정 기준을 통해 잡음 내성을 갖는 디지털 회로를 사용하는 것이 필수적이다.

## 아날로그 세계에서 디지털 만들기

    - 전이 함수 : 수학에서의 함수와 같지만 실제 세계에서 벌어지는 현상을 표현

    - 조리개나 볼륨 등은 게인(gain) 또는 곡선이 가파른 정도를 조절한다.

    - 게인이 높으면 곡선이 가팔라지고 출력도 커지지만 왜곡(입력과 출력이 달라짐)이 발생할 수 있다.

    - 입력이 조금만 변해도 곡선의 가파른 부분 때문에 출력이 확 달라지게 되는데 이때 판정 기준을 문턱값이라고 부른다.

    - 게인을 조절하는 것은 연속적인 공간을 이산적인 영역으로 나눠주며 안정성과 잡음 내성을 얻을 수 있다.

    - 아날로그는 선형 영역을 가능한 크게 만들기 위해 노력하는 것, 디짘털은 직선부를 가능한 작게 만드는 것

## 10진 숫자 대신 비트를 사용하는 이유

    - 비트를 활용하는 것이 덜 직관적이지만 더 효율적이다!

    - 10진 숫자를 사용하면 전이 함수를 각기 다른 10가지 문턱값으로 구분할 수 있는 간단한 방법이 없기 때문이다.
       (비트를 사용하는 것이 더 간단하고 비용도 덜 든다)

## 간단한 전기 이론 가이드

    - 전기를 물이라고 생각해보자

    - 물은 게이트 밸브가 열려 있을 때 통과가 가능하며, 우리는 0이 밸브가 닫힌 상태이며 1이 밸브가 열린 상태를 표현한다고 가정

    - 직렬 연결은 AND 연산, 병렬 연결은 OR 연산을 구현

    - 물이 파이프를 흘러가는 것처럼 전기도 선을 통해 이동
      (다만, 이런 전기의 이동은 실제로는 전자의 흐름은 아니고 전자기 에너지 반응이 전파되는 현상을 전기라고 부름)

    - 전기 선은 두 부분으로 구성
        - 내부에 있는 금속 (유동성이 있는 전자가 있는 곳) : 도체
        - 금속 바깥쪽을 둘러싼 부분 : 부도체
        - 전기의 흐름을 제어할 수 있는 밸브 : 스위치

    * 물과 전기 비교를 통한 용어정리
        - 파이프 안에서 물은 수압에 의해 움직임
        
        - 전기에서 수압 -> 전압이며, 측정 단위는 볼트

        - 전기 흐름의 양은 전류이며, 측정 단위는 암페어

        - 전압이 높더라고 도체가 가늘면 저항이 커져서 전류가 커지지 못한다.
            -> 저항의 측정 단위는 옴

## 비트를 처리하기 위한 하드웨어

    - 릴레이

        - 스위치를 움직이기 위해 전자석(ElectroMagnet)을 사용하는 장치

        - 스위치로는 불가능한 일을 할 수 있음
            - ex) NOT 함수를 구현하는 인버터를 만들 수 있음 (NOT이 없으면 표현 가능한 불리언 연산이 제한)
        
        - 스위치가 스위치를 제어하는 것을 가능하게 하고 이에 따라 컴퓨터에 필요한 복잡한 논리를 만들 수 있음

        - 느리고 전기를 많이 소모하며 먼지(벌레)가 스위치 접점에 있으면 제대로 작동하지 않음

    - 진공관

        - 물체를 가열하면 전자가 튀어나오는 열전자 방출이라는 현상을 기반으로 만들어짐

        - 진공관 안에는 캐소드(투수 역할)과 캐소드를 가열하는 히터가 있으며, 캐소드에서 발생한 전자(야구공)는 진공 속에서 애노드(포수 역할)로 날아간다

        - 그리드(타자 역할)도 진공관에 추가로 더할 수 있음

        - 삼극관 : 캐소드 + 그리드 + 애노드가 들어있는 진공관

        - 릴레이보다 빠르지만 뜨겁고 쉽게 깨짐

    - 트랜지스터

        - transfer resistor(전송 저항)이라는 말을 줄임

        - 진공관과 비슷하지만 반도체를 사용

        - 반도체 : 도체와 부도체 사이를 오갈 수 있는 물질 (히터나 전기밸브를 만들려면 이런 특성이 필요)

        - 트랜지스터는 반도체 물질로 이뤄진 기판 또는 슬랩 위에 만들어진다. (보통은 실리콘이 기판 재료로 쓰임)

        - 가장 중요한 두 가지 유형 : 쌍극 접합 트랜지스터(BJT), 필드 효과 트랜지스터(FET)

        - 금속산화물 반도체 전계 효과 트랜지스터(MOSFET)는 FET의 일종으로, 전력 소모가 적기 때문에 현대 컴퓨터 칩에서 가장 널리 쓰임

    - 집적 회로

        - 트랜지스터를 사용하면 AND 함수 같은 간단한 회로를 만들 때조차도 부품이 많이 필요

        - 집적 회로 = 칩

## 논리게이트

    - 칩들 안에서 논리 연산을 수행하는 회로 (= 게이트)

    - 게이틀르 사용하면 하드웨어 설계자가 밑바닥부터 모든 회로를 설계할 필요 없이 복잡한 회로를 쉽게 만들 수 있음

    - AND 게이트, OR 게이트, XOR 게이트, 인버터(NOT을 수행하는 게이트)

    - AND 게이트의 출력 Y는 게이트의 입력 A와 B가 참인 경우에만 참 (책 p.118 그림2-26 참고)

    - 인버터 기호에서 중요한 부분은 삼각형 꼭지점에 있는 동그라미이다. 동그라미가 없는 삼각형 = 버퍼, 버퍼는 단지 입력을 출력으로 전달

    - 논리게이트에서 가장 단순한 회로는 NAND나 NOR

## 이력 현상을 활용한 잡음 내성 향상

    - 디지털 장치를 사용하면 판정 기준에 의해 잡음 내성을 얻음

    - 천천히 변하는 신호의 경우, 입력 신호에 잡음이 있으면 입력 신호가 문턱값을 여러번 오락가락하기 때문에 출력 신호에 글리치(작은 오류)가 생긴다.

    - 글리치는 이력 현상을 사용해 방지 가능

    - 이력 현상 : 판정 기준이 이력(과거에 벌어진 일)에 따라 달라진다는 뜻

    - 슈미트 트리거 : 이력을 사용하는 게이트

## 차동 신호

    - 차동 신호 : 서로 반전관계인 신호 쌍의 차이를 측정

    - 전화선 등 여러 곳에서 차동 신호를 사용하며, 오늘날 USB ,이더넷 케이블 등에 쓰이는 연선 케이블링이 대표적인 예

## 전파 지연

    - 입력의 변화가 출력에 영향을 미칠 때까지 걸리는 시간

    - 논리 회로의 최대 속도를 제한하는 요소 중 하나        

## 출력 유형
    
    - 토템폴 출력

        - 일반적인 게이트 출력

    - 오픈 컬렉터 출력

        - 오픈 컬렉터나 오픈 드레인 출력이 있음

    - 트라이스테이트 출력

## 게이트를 조합한 복잡한 회로

    - 가산기

    - 디코더

        - 인코딩된 수를 개별 비트의 집합으로 만들어줌

    - 디멀티플렉서

        - 디코더를 사용해 만들 수 있음

        - 디먹스라고 줄여서 불리기도 함

    - 실렉터

        - 여러 입력 중 입력을 선택하는 기능을 수행

        - 게이트를 사용하면 실렉터 또는 멀티플렉서(=먹스)를 만들 수 있음

