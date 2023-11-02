package ru.clevertec.product.util;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Builder(setterPrefix = "with", toBuilder = true)
public class TestDataBuilder {

    @Builder.Default
    private UUID uuid = UUID.fromString("d3a9fd54-6622-4233-8618-fc0b320ca457");

    @Builder.Default
    private String name = "Аппельсин";

    @Builder.Default
    private String description = "Сладкий и очень вкусный";

    @Builder.Default
    private BigDecimal price = BigDecimal.TEN;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.of(2023, 11, 14, 10, 45);

    public Product buildProduct() {
        return Product.builder()
                .uuid(uuid)
                .name(name)
                .description(description)
                .price(price)
                .created(created)
                .build();

    }

    public ProductDto buildProductDto() {
        return new ProductDto(name, description, price);

    }

    public InfoProductDto buildInfoProductDto() {
        return new InfoProductDto(uuid, name, description, price);
    }
}
