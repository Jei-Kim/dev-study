# Chapter07 - 데이터 구조와 처리 - 어떻게 해야 프로그램에서 데이터를 잘 구성하고 처리할까 

    - 데이터 구조 : 데이터를 조직화하는 표준적인 방법

    - 참조 지역성 : '필요한 데이터를 메모리 내에서 서로 근처에 유지하라, 금방 사용할 데이터라면 더 가까운 곳에 저장하라'는 의미

## 기본 데이터 타입

    - 기본 데이터 타입에는 크기와 해석이라는 두 가지 측면이 존재

    - 포인터 (C언어) : 컴퓨터 아키텍처에 따라 결정되는 크기의 부호가 없는 정수에 불과하며, 정숫값이 아니라 메모리 주소로 해석

## 배열

    - 인덱스 : 배열 칸의 번호 (0부터 시작)

    - 엘리먼트 : 배열의 값

    - 오프셋 : 기저 주소(0번째 값의 주소)로부터 얼마나 멀리 떨어져 있는지를 나타냄

## 비트맵

    - 비트맵 : 비트의 배열

    - 기본연산은 4가지 (비트 설정하기(1로 만들기), 비트 지우기(0으로 만들기, 비트가 1인지 검사하기, 비트가 0인지 검사하기)

## 문자열

    - 문자열 : 여러 문자로 이루어진 시퀀스

    - 문자열 터미네이터 : 문자열 끝을 표시하는 문자

## 복합 데이터 타입

    - 구조체 : 사용자가 원하는대로 데이터 타입을 만들 수 있는 방법

## 단일 연결 리스트

    - 목록에 들어갈 원소 개수를 모르는 경우 배열 대신 사용

    - next : 리스트의 다음 원소 주소를 저장하는 포인터

    - head : 리스트의 맨 앞

    - null 포인터로 리스트 원소가 아님을 표현

    - 리스트에 원소 추가 => 헤트 앞에 새 원소를 위치

    - 리스트에서 원소 삭제 => next가 삭제할 원소 다음 원소를 가리키게 변경

## 동적 메모리 할당

    - 배열 등의 변수가 사용하는 메모리는 정적 메모리
        -> 이런 변수에 할당된 주소는 바뀌지 않음

    - 리스트 노드와 같은 존재는 동적
        -> 필요에 따라 생기기도 하고 사라지기도 함, 동적 메모리는 힙에서 얻음

## 더 효율적인 메모리 할당

    - 노드와 문자열을 동시에 할당하면 효율적인 메모리 할당 가능

## 가비지 컬렉션

    - 참조 : 포인터를 추상화해서 거의 비슷한 기능을 제공하지만 실제 메모리 주소를 노출하지는 않음

    - 언어의 런타임 환경이 변수 사용을 추적해서 더 이상 사용하지 않는 메모리를 자동으로 해제

    - 프로그래머가 가비지 컬렉션 시스템을 제어 x

## 이중 연결 리스트

    - 노드에 다음 원소에 대한 포인터뿐만 아니라 이전 원소에 대한 포인터들도 들어있음

    - 단일 연결 리스트는 리스트 전체를 순회해야 하기 때문에 느리지만, 이중 연결 리스트는 순회할 필요가 없으므로 빠름 But, 메모리 사용이 큼

## 계층적인 데이터 구조

    - 2진 트리 : 가장 간단한 계층적 데이터 구조 (2진 : 노드가 최대 2개의 다른 노드와 연결 가능하다는 의미)

    - 트리의 루트는 연결 리스트의 헤드에 해당

    - 2진 트리에서 어떤 대상을 검색하는 연산은 트리 깊이에 의해 정의되는 함수
        -> 트리가 n계층만큼 아래로 내려가면 검색 시 n번만 원소를 비교하면 됨
        -> 연결된 리스트(비균형 2진 트리)에서는 n번 검색
        -> 균형 2진 트리에서는 log2n번만 비교
            -> ex) 연결된 리스트는 1024번, 균형 트리에서는 10번 

## 대용량 저장장치

    - 블록 : 디스크의 기본 단위

    - 클러스터 : 연속적인 블록

    - 데이터를 장기적으로 저장할 때는 디스크를 사용하므로, 데이터를 디스크에 저장하기 위해서는 파일 이름(영구적인 존재)이 필요

    - 블록 중 일부를 아이노드로 지정 (아이노드 = 디스크 블록에 대한 인덱스 + 노드)

    - 아이노드에는 파일 이름, 파일 소유자, 파일 크기, 파일에 대한 허가 내역 등이 포함

    - 파일이 커지면 간접 블록을 사용

    - 아이노드 정보 중에는 블록에 데이터가 있는지 디렉토리 정보가 있는지를 표시

    - 디렉토리는 다른 디렉토리를 참조 가능 -> 계층적 파일 시스템 탄생

    - 각각의 참조 = 링크

## 데이터베이스

    - 데이터베이스 : 정해진 방식으로 조직화된 데이터 모음

    - 데이터베이스 관리 시스템(DBMS) : 데이터베이스에 정보를 저장하고 읽어올 수 있게 해주는 프로그램
        - 맨 아래의 데이터 저장 메커니즘을 감싼 여러 계층의 인터페이스로 구성

    - B 트리라는 데이터 구조를 활용한 시스템
        - B 트리는 균형 트리지만 2진 트리는 아님
        - B 트리는 균형 2진 트리보다는 공간을 덜 효율적으로 사용하지만 성능이 더 낫고, 디스크에 데이터를 저장할 때 균형 2진 트리보다 성능이 좋음
        - B 트리는 메모리 아키텍처에 대한 이해가 더 효율적인 코드를 작성하는 데 도움이 됨

## 인덱스

    - 데이터에 더 효율적으로 접근할 수 있게 도와줌

    - 데이터가 바뀔 때마다 모든 인덱스를 갱신해야함
        - 데이터 변경보다는 검색이 더 빈번하므로 갱신은 지불할 만한 비용!

## 데이터 이동

    - 루프 언롤링 기법, 더프의 장치 등의 방식으로 이동

## 벡터를 사용한 I/O

    - 시스템에게 프레임의 각 부분을 가리키는 포인터의 집합을 전달하고, 시스템이 출력 장치에 데이터를 쓸 때 각 부분을 하나로 합쳐주는게 효율적

    - 수집 : 벡터를 활용해 데이터를 쓰는 행위

    - 분산 : 벡터를 사용해 데이터를 읽는 행위

    * 벡터 : 같은 데이터가 연속적으로 모여 있는 고정된 크기의 데이터 구조

## 객체 지향의 함정

    - 객체에는 함수에 해당하는 메서드와 데이터에 해당하는 프로퍼티가 있음

    - 어떤 객체에 필요한 모든 데이터와 함수는 한 데이터 구조 안에 모여 있음

    - 객체는 전역적으로 알려진 함수 대신에 자신이 사용할 메서드에 대한 포인터를 가지고 있어야 하므로 객체 내의 데이터가 데이터만 저장하는 데이터 구조처럼 꽉 짜여 있지 않음
        - 성능이 중요할 때는 전통적인 배열 활용

## 정렬

    - 데이터를 정렬해서 저장할 경우, 메모리 접근 횟수를 줄임으로써 검색을 빨리 끝낼 수 있음

    - 정렬 대상이 포인터 크기보다 크면 데이터를 직접 정렬하는 대신 데이터를 가리키는 포인터를 재배열하는 방식으로 정렬

## 해시

    - 검색에 사용할 키에 대해 이들을 균일하게 뿌려주는 해시 함수를 적용

    - 해시 함수의 결괏값을 사용해 키에 대응하는 데이터를 메모리에 저장

    - 해시 테이블 : 저장장치에 데이터를 저장하는 방법 중에서 해시 함수의 결과를 배열 인덱스로 활용하는 방법

    - 해시 함수의 값이 같을 경우 충돌 발생
        - 해시 체인을 사용해 충돌 해결

## 효율성과 성능

    - 데이터베이스 샤딩 : 데이터베이스를 각각 다른 기계에서 실행되는 여러 샤드로 나누는 방식 (=수평 파티셔닝)
        - 인터페이스를 통해 요청이 들어온 데이터베이스 연산을 모든 샤드에 전달하고 컨트롤러가 결과를 하나로 모음

    - 맵리듀스(=샤딩의 변형) : 근본적으로 컨트롤러가 중간 결과를 모으는 방법을 코드로 직접 작성할 수 있게 해줌