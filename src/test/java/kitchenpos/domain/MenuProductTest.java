package kitchenpos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    private Product product;

    @BeforeEach
    void before() {
        product = new Product("짜장", BigDecimal.valueOf(5000L));
    }
    @Test
    @DisplayName("메뉴 상품의 총계를 구한다.")
    void calculateTest() {
        MenuProduct menuProduct = new MenuProduct(product, 3);
        BigDecimal result = menuProduct.calculateAmount();
        assertThat(result).isEqualTo(BigDecimal.valueOf(15000L));
    }
}