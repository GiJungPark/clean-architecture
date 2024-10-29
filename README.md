
## 계층형 아키텍처의 문제점
### 계층형 아키텍처는 데이터베이스 주도 설계를 유도한다.
- 웹 계층은 도메인 계층에 의존하고, 도메인 계층은 영속성 계층에 의존하게 되는 특징으로 인해 영속성 계층에 의존적인 도메인 계층을 만들게 되기 쉽다.
- ORM 프레임워크와 계층형 아키텍처의 결합으로, 비즈니스 규칙을 영속성 관점과 섞고 싶은 유혹을 일으킨다.
### 지름길을 택하기 쉬워진다.
- 상위 계층에 위치한 컴포넌트에 접근해야 하는 상황에서, 컴포넌트를 계층 아래로 내려버리면 해결 되기 때문에 이러한 방향으로 흘러갈 상황이 생길 수 있다.
### 테스트하기 어려워진다.
- 단 하나의 필드를 조작하는 것에 불과하더라도 도메인 로직을 웹 계층에 구현하게 된다.
- 웹 계층 테스트에서 도메인 계층뿐만 아니라 영속성 계층도 mocking 해야 한다.
### 유스케이스를 숨긴다.
- 아키텍처는 코드를 빠르게 탐색하는 데 도움이 돼야 한다.
- 계층형 아키텍처는 도메인 서비스의 너비에 관한 규칙을 강제하기 않기 때문에, 유스케이스를 책임지는 서비스를 찾기 어려워진다.
### 동시 작업이 어려워진다.
- 코드에 넓은 서비스가 있다면 서로 다른 기능을 동시에 작업하기 어려워진다.
### 계층형 아키텍처의 개인적인 생각
- 위와 같은 문제점들 있다고 `사용하면 안된다!`는 절대 아니라고 생각한다.
- `전반적인 도메인이 수립되지 않은 상황`에서, 계층형 아키텍처는 빠르게 해당 프로젝트를 알아볼 수 있는 수단이라고 생각한다.

<br>

## 클린 아키텍처 & 헥사고날 아키텍처
### 클린 아키텍처
- 도메인 코드가 바깥으로 향하는 어떤 의존성도 없어야 한다.
- 대신 `의존성 역전 원칙`의 도움으로 모든 의존성이 도메인 코드를 향해야 한다.
#### 클린 아키텍처의 장점
- 설계가 비즈니스 규칙의 테스트를 용이하게 한다.
- 비즈니스 규칙은 Framework, Database, UI 기술 등 외부 애플리케이션이나 인터페이스로 부터 독립적일 수 있다.
#### 클린 아키텍처의 단점?
- ORM 프레임워크를 사용하는 경우
  - 도메인 계층과 영속성 계층이 데이터를 주고 받을 때, 두 엔티티를 서로 변환해야 한다.
  - 하지만 이는 `결합이 제거된 상태`이기 때문에 `바람직한 일`이다.
### 헥사고날 아키텍처
![img.png](https://private-user-images.githubusercontent.com/101462387/381067213-71862d79-e973-46e4-8628-64a96e9df7ee.jpeg?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAxOTUyMzYsIm5iZiI6MTczMDE5NDkzNiwicGF0aCI6Ii8xMDE0NjIzODcvMzgxMDY3MjEzLTcxODYyZDc5LWU5NzMtNDZlNC04NjI4LTY0YTk2ZTlkZjdlZS5qcGVnP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI0MTAyOSUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNDEwMjlUMDk0MjE2WiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9MzA4NzZjOGY0ZjIxZGFkNjAyNGFjNGMxYjE1YjFmZjYyMjFjNWQyMDMwMDQ2YmZmNDNlZTk1MzNhZDMzZDdlZCZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QifQ.A1AqPTHmfVTHfkqLcXlXy4kxUzRVn9tcER91wlWnvdI)
- `에플리케이션 코어`가 각 `어댑터`와 상호작용하기 위해 특정 `포트`를 제공한다.
#### 어댑터
- 어댑터는 크게 두 가지의 종류가 있다.
  - 애플리케이션을 주도하는 어댑터 (애플리케이션 코어를 호출하는)
  - 애플리케이션에 의해 주도되는 어댑터 (애플리케이션 코어에 의해 호출되는)
#### 포트
- 애플리케이션 코어와 어댑터들 간의 통신이 가능하려면, `애플리케이션 코어`가 `각각의 포트를 제공`해야 한다.
  - 애플리케이션을 주도하는 어댑터의 포트
    - 포트는 코어에 있는 유스케이스 클래스 중 하나에 의해 구현되고 어댑터에 의해 호출되는 인터페이스가 된다.
  - 애플리케이션에 의해 주도되는 어댑터의 포트
    - 포트는 어댑터에 의해 구현되고 코어에 의해 호출되는 인터페이스가 된다.