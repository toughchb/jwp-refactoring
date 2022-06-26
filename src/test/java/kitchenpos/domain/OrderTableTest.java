package kitchenpos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTableTest {

    OrderTable orderTable;
    TableGroup tableGroup;

    @BeforeEach
    void before() {
        orderTable = new OrderTable(0, true);
        tableGroup = new TableGroup();
    }
    @Test
    @DisplayName("주문 테이블에 단체 지정을 할 수 잇다.")
    void attachTest() {
        //when
        orderTable.attachToTableGroup(tableGroup);

        //then
        assertThat(orderTable.getTableGroup()).isNotNull();
    }

    @Test
    @DisplayName("주문 테이블에 손님수를 변경 할 수 잇다.")
    void guestTest() {
        //when
        orderTable.updateNumberOfGuests(5);

        //then
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(5);
    }


    @Test
    @DisplayName("주문 테이블을 빈테이블로 변경 할 수 잇다.")
    void emptyTest() {
        //when
        OrderTable nonEmptyTable = new OrderTable(3, false);
        nonEmptyTable.switchEmpty(true);

        //then
        assertThat(nonEmptyTable.isEmpty()).isTrue();
    }
}