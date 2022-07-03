package kitchenpos.menus.menu.application;

import java.util.List;
import kitchenpos.menus.menu.domain.MenuRepository;
import kitchenpos.menus.menu.dto.MenuRequest;
import kitchenpos.menus.menu.dto.MenuResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuValidator menuValidator;

    public MenuService(MenuRepository menuRepository, MenuValidator menuValidator) {
        this.menuRepository = menuRepository;
        this.menuValidator = menuValidator;
    }

    @Transactional
    public MenuResponse create(final MenuRequest request) {
        menuValidator.validate(request);
        return MenuResponse.from(menuRepository.save(request.toMenu()));
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> list() {
        return MenuResponse.from(menuRepository.findAll());
    }
}