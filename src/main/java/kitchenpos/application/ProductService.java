package kitchenpos.application;

import kitchenpos.domain.ProductRepository;
import kitchenpos.dto.ProductRequest;
import kitchenpos.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        return ProductResponse.of(productRepository.save(request.toProduct()));
    }

    public List<ProductResponse> list() {
        return ProductResponse.of(productRepository.findAll());
    }
}
