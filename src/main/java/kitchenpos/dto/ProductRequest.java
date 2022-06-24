package kitchenpos.dto;

import java.math.BigDecimal;
import kitchenpos.domain.Product;

public class ProductRequest {

    private String name;
    private BigDecimal price;

    public ProductRequest() {
    }

    protected ProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public static ProductRequest of(String name, BigDecimal price) {
        return new ProductRequest(name, price);
    }

    public Product toProduct() {
        return new Product(name, price);
    }
    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
