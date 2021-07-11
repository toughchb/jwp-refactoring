# 키친포스

## 기능 정리

- [x] 메뉴 그룹 기능

  - [x] 메뉴 그룹명을 입력받아 메뉴 그룹을 생성한다.
  - [x] 메뉴 그룹 목록을 조회한다.

- [x] 메뉴 기능

  - [x] 메뉴를 생성한다.

    - 메뉴 가격이 0보다 작거나 입력되지 않으면 오류가 발생

    - 메뉴 그룹이 존재하지 않으면 오류 발생

    - 메뉴 가격이 메뉴 상품 금액보다 크면  오류 발생

  - [x] 메뉴 목록을 조회한다.

    - 메뉴 목록에는 메뉴 상품 목록을 포함한다.

- [x] 주문 기능

  - [x] 주문 테이블, 주문 항목 등 정보를 통해 주문 한다.
    - 주문 항목이 없으면 오류 발생
    - 주문 항목의 수가 메뉴에 있는 주문항목의 수와 다르면 오류 발생
    - 주문 테이블 정보가 없으면 오류 발생
    - 주문 상태는 COOKING 으로 초기화
  - [x] 주문 목록을 조회한다.
    - 주문 항목을 포함한다.
  - [x] 주문의 주문 상태를 변경한다.
    - 주문 정보가 없으면 오류 발생
    - 주문이 이미 완료된 상태(COMPLETION)이면 오류 발생

- [x] 상품 기능

  - [x] 상품명과 가격정보로 상품을 생성한다.
    - 상품 가격이 0보다 작으면 오류 발생
  - [x] 상품 목록을 조회한다.

- [x] 단체 지정 기능

  - [x] 주문 테이블 정보를 통해 단체 지정이 가능하다.
    - 주문 테이블 정보가 없거나 2테이블 미만으로 단체를 지정하면 오류발생
    - 단체 지정된 주문테이블과 저장된 주문테이블의 수가 다르면 오류발생
  - [x] 단체 지정을 해제한다.
    - 단체 지정의 주문 테이블의 상태가 조리중, 식사중(COOKING, MEAL) 인 상태이면 오류 발생

- [x] 주문 테이블 기능

  - [x] 방문한 손님 수, 빈 테이블 정보를 통해 주문 테이블을 생성한다.
    - 최초 생성시 단체 지정 없이 생성
  - [x] 주문 테이블 목록을 조회한다.
  - [x] 주문 테이블의 빈 테이블 여부를 변경한다.
    - 주문 테이블 정보가 없으면 오류 발생
    - 주문 테이블이 단체지정이 되어있는경우 오류 발생
    - 주문 테이블의 상태가 조리중, 식사중(COOKING, MEAL)인 경우 오류 발생
  - [x] 주문 테이블의 방문한 손님 수를 변경한다.
    - 변경해야 할 방문한 손님수가 0보다 작으면 오류 발생
    - 주문 테이블 정보가 없으면 오류 발생
    - 주문 테이블이 빈 테이블인 경우 오류 발생

## 요구 사항

### Step1 - 테스트를 통한 코드 보호

- [x] `kitchenpos` 코드를 통해 요구사항을 정리한다.

- [x] 정리한 요구사항을 토대로 테스트 코드를 작성한다.

  - Business Object 에 대한 테스트 코드를 작성
  - `@ExtendWith(MockitoExtension.class)`를 이용

  - [x] MenuGroupServiceTest 구현
  - [x] MenuServiceTest 구현
  - [x] OrderServiceTest 구현
  - [x] ProductServiceTest 구현
  - [x] TableGroupServiceTest 구현
  - [x] TableServiceTest 구현

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |

