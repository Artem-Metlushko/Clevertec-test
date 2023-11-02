package ru.clevertec.product.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.TestDataBuilder;

import java.util.UUID;
import java.util.stream.Stream;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private ProductMapper productMapper = new ProductMapperImpl();

    @Test
    void toProductShouldReturnProductWithDtoFields() {
        //given
        ProductDto mockProductDto = TestDataBuilder.builder()
                .withPrice(valueOf(100))
                .build().buildProductDto();

        //when
        Product actual = productMapper.toProduct(mockProductDto);

        //then
        assertThat(actual)
                .hasFieldOrPropertyWithValue("name", mockProductDto.name())
                .hasFieldOrPropertyWithValue("description", mockProductDto.description())
                .hasFieldOrPropertyWithValue("price", mockProductDto.price());
    }

    @Test
    void toInfoProductDtoShouldReturnValidResult() {
        //given
        Product mockProduct = TestDataBuilder.builder()
                .withName("Картоха")
                .build().buildProduct();

        //when
        InfoProductDto actual = productMapper.toInfoProductDto(mockProduct);

        //then
        assertThat(actual)
                .hasFieldOrPropertyWithValue("name", mockProduct.getName())
                .hasFieldOrPropertyWithValue("uuid", mockProduct.getUuid())
                .hasFieldOrPropertyWithValue("description", mockProduct.getDescription())
                .hasFieldOrPropertyWithValue("price", mockProduct.getPrice());
    }

    @Test
    void mergeShouldReturnProduct() {
        //given
        Product mockProduct = TestDataBuilder.builder().build().buildProduct();
        ProductDto mockProductDto = TestDataBuilder.builder().build().buildProductDto();

        //when
        Product actual = productMapper.merge(mockProduct, mockProductDto);

        //then
        assertThat(actual)
                .hasFieldOrPropertyWithValue("name", mockProductDto.name())
                .hasFieldOrPropertyWithValue("description", mockProductDto.description())
                .hasFieldOrPropertyWithValue("price", mockProductDto.price())
                .hasFieldOrPropertyWithValue("created", mockProduct.getCreated());
    }
    @ParameterizedTest
    @MethodSource("provideListProduct")
    void toProductShouldHaveUuid(Product mockProduct, boolean expected) {
        //given

        //when
        InfoProductDto infoProductDto = productMapper.toInfoProductDto(mockProduct);

        //then
        assertEquals(expected, infoProductDto.uuid() != null);
    }
    private static Stream<Arguments> provideListProduct() {
        boolean blank = !new UUID(0, 0).toString().isBlank();
        return Stream.of(
                Arguments.of(TestDataBuilder.builder()
                        .withUuid(null)
                        .build().buildProduct(), false),
                Arguments.of(TestDataBuilder.builder()
                        .withUuid(UUID.fromString("9755b24d-beba-4cef-b397-2e1f429385d3"))
                        .build().buildProduct(), true),
                Arguments.of(TestDataBuilder.builder()
                        .withUuid(new UUID(0, 0))
                        .build().buildProduct(), blank)
        );
    }
}
