package kitchenpos.application;

import kitchenpos.dao.MenuDao;
import kitchenpos.dao.MenuGroupDao;
import kitchenpos.dao.MenuProductDao;
import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    MenuDao menuDao;

    @Mock
    MenuGroupDao menuGroupDao;

    @Mock
    MenuProductDao menuProductDao;

    @Mock
    ProductDao productDao;

    @InjectMocks
    private MenuService menuService;

    private MenuProduct menuProduct;
    private List<MenuProduct> menuProducts;
    private Product product;
    private Menu menu1;
    private Menu menu2;

    @BeforeEach
    void setUp() {
        menuProduct = new MenuProduct(1L, 1L, 1L, 1);
        menuProducts = Arrays.asList(menuProduct);
        product = new Product(1L, "신메뉴", BigDecimal.valueOf(20000));
        menu1 = new Menu(1L, "신메뉴", BigDecimal.valueOf(20000), 1L, menuProducts);
        menu2 = new Menu(2L, "신메뉴", null, 1L, menuProducts);
    }

    @DisplayName("메뉴를 등록한다. (메뉴 상품(MenuProduct) 리스트에도 메뉴를 등록한다.)")
    @Test
    void create() {
        given(menuGroupDao.existsById(1L)).willReturn(true);
        given(productDao.findById(anyLong())).willReturn(Optional.of(product));
        given(menuDao.save(any())).willReturn(menu1);
        given(menuProductDao.save(any())).willReturn(menuProduct);

        Menu savedMenu = menuService.create(menu1);

        assertAll(
                () -> assertThat(savedMenu).isEqualTo(menu1),
                () -> assertThat(savedMenu.getMenuProducts()).contains(menuProduct));
    }

    @DisplayName("메뉴를 등록에 실패한다 - 메뉴 가격이 null 이거나 0보다 작을 경우")
    @Test
    void fail_create1() {
        Menu menu1 = new Menu(1L, "신메뉴", BigDecimal.valueOf(-1), 1L, menuProducts);
        Menu menu2 = new Menu(2L, "신메뉴", null, 1L, menuProducts);

        assertThatThrownBy(() -> menuService.create(menu1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> menuService.create(menu2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 등록에 실패한다 - 메뉴 그룹 아이디가 등록되어 있지 않은 경우")
    @Test
    void fail_create2() {
        Menu menu = new Menu(1L, "신메뉴", BigDecimal.valueOf(18000), 1L, menuProducts);

        assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 등록에 실패한다 - 메뉴 등록시 메뉴 상품들의 총 가격(상품 * 수량의 총합) 보다 클 수 없다.")
    @Test
    void fail_create3() {
        Product product = new Product(1L, "신메뉴", BigDecimal.valueOf(2000));
        given(menuGroupDao.existsById(1L)).willReturn(true);
        given(productDao.findById(anyLong())).willReturn(Optional.of(product));

        assertThatThrownBy(() -> menuService.create(menu1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 리스트를 조회한다. (메뉴 조회시 메뉴에 대한 수량 정보도 같이 가져온다.)")
    @Test
    void list() {
        List<MenuProduct> menuProducts1 = Arrays.asList(new MenuProduct(1L, 1L, 1L, 1L));
        List<MenuProduct> menuProducts2 = Arrays.asList(new MenuProduct(2L, 2L, 2L, 1L));
        List<MenuProduct> menuProducts3 = Arrays.asList(new MenuProduct(3L, 3L, 3L, 1L));
        Menu menu1 = new Menu(1L, "메뉴1", BigDecimal.valueOf(15000), 1L, menuProducts1);
        Menu menu2 = new Menu(2L, "메뉴2", BigDecimal.valueOf(17000), 1L, menuProducts2);
        Menu menu3 = new Menu(3L, "메뉴3", BigDecimal.valueOf(15000), 1L, menuProducts3);
        List<Menu> menus = Arrays.asList(menu1, menu2, menu3);
        given(menuDao.findAll()).willReturn(menus);
        given(menuProductDao.findAllByMenuId(1L)).willReturn(menuProducts1);
        given(menuProductDao.findAllByMenuId(2L)).willReturn(menuProducts2);
        given(menuProductDao.findAllByMenuId(3L)).willReturn(menuProducts3);

        List<Menu> selectedMenus = menuService.list();

        assertAll(
                () -> assertThat(selectedMenus).isEqualTo(menus),
                () -> assertThat(selectedMenus.get(0).getMenuProducts()).isEqualTo(menuProducts1),
                () -> assertThat(selectedMenus.get(1).getMenuProducts()).isEqualTo(menuProducts2),
                () -> assertThat(selectedMenus.get(2).getMenuProducts()).isEqualTo(menuProducts3));
    }
}