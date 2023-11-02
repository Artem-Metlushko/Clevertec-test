package ru.clevertec.product.mapper;

import org.assertj.core.api.Assertions;
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
        Product expected = TestDataBuilder.builder()
                .withName("Картоха")
                .build().buildProduct();

        //when
        InfoProductDto actual = productMapper.toInfoProductDto(expected);

        //then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue("uuid", expected.getUuid())
                .hasFieldOrPropertyWithValue("description", expected.getDescription())
                .hasFieldOrPropertyWithValue("price", expected.getPrice());
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
                .hasFieldOrPropertyWithValue(Product.Fields.name, mockProductDto.name())
                .hasFieldOrPropertyWithValue(Product.Fields.description, mockProductDto.description())
                .hasFieldOrPropertyWithValue(Product.Fields.price, mockProductDto.price())
                .hasFieldOrPropertyWithValue(Product.Fields.created, mockProduct.getCreated());
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
        return Stream.of(
                Arguments.of(TestDataBuilder.builder()
                        .withUuid(null)
                        .build().buildProduct(), false),
                Arguments.of(TestDataBuilder.builder()
                        .withUuid(UUID.fromString("9755b24d-beba-4cef-b397-2e1f429385d3"))
                        .build().buildProduct(), true)
        );
    }
}
