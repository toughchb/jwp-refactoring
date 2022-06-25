package kitchenpos.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import kitchenpos.application.fixture.MenuFixtureFactory;
import kitchenpos.application.fixture.MenuGroupFixtureFactory;
import kitchenpos.application.fixture.MenuProductFixtureFactory;
import kitchenpos.application.fixture.ProductFixtureFactory;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuGroupRepository;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.MenuProductRepository;
import kitchenpos.domain.MenuRepository;
import kitchenpos.domain.Product;
import kitchenpos.domain.ProductRepository;
import kitchenpos.dto.MenuRequest;
import kitchenpos.dto.MenuResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @Mock
    private MenuProductRepository menuProductRepository;


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private MenuService menuService;

    private Menu 중식메뉴;
    private MenuGroup 중식;
    private MenuProduct 중식_메뉴_짬뽕;
    private MenuProduct 중식_메뉴_짜장;
    private Product 짬뽕;
    private Product 짜장;

    @BeforeEach
    void before() {
        중식 = MenuGroupFixtureFactory.create(1L, "메뉴그룹1");
        중식메뉴 = MenuFixtureFactory.create(1L, "메뉴1", BigDecimal.valueOf(3000), 중식);

        짬뽕 = ProductFixtureFactory.create(1L, "상품1", BigDecimal.valueOf(1000));
        짜장 = ProductFixtureFactory.create(2L, "상품2", BigDecimal.valueOf(2000));

        중식_메뉴_짬뽕 = MenuProductFixtureFactory.create(1L, 중식메뉴, 짬뽕, 3);
        중식_메뉴_짜장 = MenuProductFixtureFactory.create(2L, 중식메뉴, 짜장, 1);

        중식메뉴.addMenuProduct(Arrays.asList(중식_메뉴_짬뽕, 중식_메뉴_짜장));
    }

    @Test
    @DisplayName("생성 하려는 메뉴의 메뉴 그룹이 시스템에 존재 하지 않으면 추가 할 수 없다.")
    void createTestFailWithMenuGroupNotExist() {
        //given
        given(menuGroupRepository.existsById(중식.getId())).willReturn(false);

        //when & then
        assertThatThrownBy(
                () -> menuService.create(MenuRequest.from(중식메뉴))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("생성 하려는 메뉴의 메뉴 상품이 시스템에 등록 되어 있지 않으면 추가 할 수 없다.")
    void createTestFailWithMenuProductNotExist() {
        //given
        given(menuGroupRepository.existsById(중식.getId())).willReturn(true);
        given(productRepository.findById(중식_메뉴_짬뽕.getProduct().getId())).willThrow(IllegalArgumentException.class);

        //when & then
        assertThatThrownBy(
                () -> menuService.create(MenuRequest.from(중식메뉴))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("생성 하려는 메뉴 가격이 전체 메뉴상품의 전체 금액(가격 * 수량의 총합)보다 클 수 없다.")
    void createTestFailWithAmount() {
        //given
        given(menuGroupRepository.existsById(중식.getId())).willReturn(true);
        given(productRepository.findById(중식_메뉴_짬뽕.getProduct().getId())).willReturn(Optional.of(짬뽕));
        given(productRepository.findById(중식_메뉴_짜장.getProduct().getId())).willReturn(Optional.of(짜장));
        Menu 잘못된_메뉴 = new Menu(1L, "잘못된 메뉴", BigDecimal.valueOf(100_000), 중식);
        잘못된_메뉴.addMenuProduct(Arrays.asList(중식_메뉴_짬뽕, 중식_메뉴_짜장));
        //when & then
        assertThatThrownBy(
                () -> menuService.create(MenuRequest.from(잘못된_메뉴))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴를 생성 할 수 있다.")
    void createTest() {
        //given
        given(menuGroupRepository.findById(중식.getId())).willReturn(Optional.of(중식));
        given(productRepository.findById(중식_메뉴_짬뽕.getProduct().getId())).willReturn(Optional.of(짬뽕));
        given(productRepository.findById(중식_메뉴_짜장.getProduct().getId())).willReturn(Optional.of(짜장));
        given(menuRepository.save(any(Menu.class))).willReturn(중식메뉴);
        중식메뉴.addMenuProduct(Arrays.asList(중식_메뉴_짬뽕, 중식_메뉴_짜장));

        //when
        MenuResponse menu = menuService.create(MenuRequest.from(중식메뉴));

        //then
        assertThat(menu).isEqualTo(MenuRequest.from(중식메뉴));
    }

    @Test
    @DisplayName("메뉴의 목록을 조회 할 수 있다.")
    void listTest() {
        //given
        given(menuRepository.findAll()).willReturn(Arrays.asList(중식메뉴));

        //when
        List<MenuResponse> menus = menuService.list();

        //then
        assertThat(menus).containsExactly(MenuResponse.from(중식메뉴));
    }
}
