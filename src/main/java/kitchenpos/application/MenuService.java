package kitchenpos.application;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuGroupRepository;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.MenuProductRepository;
import kitchenpos.domain.MenuRepository;
import kitchenpos.domain.Product;
import kitchenpos.domain.ProductRepository;
import kitchenpos.dto.MenuProductRequest;
import kitchenpos.dto.MenuRequest;
import kitchenpos.dto.MenuResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuProductRepository menuProductRepository;
    private final ProductRepository productRepository;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final MenuProductRepository menuProductRepository,
            final ProductRepository productRepository
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuProductRepository = menuProductRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public MenuResponse create(final MenuRequest request) {
        Menu menu = registerMenuGroupToMenu(request);
        List<MenuProduct> menuProducts = makeMenuProductsForAdding(request, menu);
        menu.addMenuProduct(menuProducts);
        return MenuResponse.from(menuRepository.save(menu));
    }

    private List<MenuProduct> makeMenuProductsForAdding(MenuRequest request, Menu menu) {
        List<MenuProductRequest> menuProducts = request.getMenuProducts();
        return menuProducts.stream().map(menuProduct ->
            new MenuProduct(
                menu,
                findProductByProductId(menuProduct.getProductId()),
                menuProduct.getQuantity())
        ).collect(Collectors.toList());
    }

    private Product findProductByProductId(Long productId) {
        return productRepository.
                findById(productId).
                orElseThrow(NoSuchElementException::new);
    }

    private Menu registerMenuGroupToMenu(MenuRequest request) {
        MenuGroup menuGroup = findMenuGroup(request.getMenuGroupId());
        Menu menu = request.toMenu();
        menu.registerMenuGroup(menuGroup);
        return menu;
    }

    private MenuGroup findMenuGroup(Long menuGroupId) {
        return menuGroupRepository.
                findById(menuGroupId).
                orElseThrow(NoSuchElementException::new);
    }

    public List<MenuResponse> list() {
        return MenuResponse.from(menuRepository.findAll());
    }
}
