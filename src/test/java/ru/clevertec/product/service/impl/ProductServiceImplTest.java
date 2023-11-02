package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.TestDataBuilder;
import ru.clevertec.product.validation.Validate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository repository;
    @Mock
    private ProductMapper mapper;
    @Mock
    private Validate validate;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @ParameterizedTest
    @DisplayName("Get should return info product")
    @ValueSource(strings = {
            "4f75244c-0195-4753-ad41-dd9767c82aca",
            "d469fdf4-f12d-42b8-b0ed-5a82dd"
    })
    void getShouldReturnInfoProduct(String uuidString) {
        //given
        UUID uuid = UUID.fromString(uuidString);
        InfoProductDto info = TestDataBuilder.builder().build().buildInfoProductDto();
        Product productForFind = TestDataBuilder.builder().build().buildProduct();
        doReturn(Optional.of(productForFind))
                .when(repository).findById(uuid);
        when(mapper.toInfoProductDto(productForFind))
                .thenReturn(info);

        //when
        productService.get(uuid);

        //then

        verify(repository).findById(uuidCaptor.capture());
        verify(repository, times(1)).findById(uuid);
        assertEquals(uuid, uuidCaptor.getValue());
    }

    @Test
    void getAllShouldReturnListOfInfoProductDto() {
        //given

        TestDataBuilder.builder();
        Product product = TestDataBuilder.builder()
                .withName("Манго")
                .build().buildProduct();
        Product product2 = TestDataBuilder.builder()
                .withName("Апельсин")
                .build().buildProduct();
        InfoProductDto infoProductDto = TestDataBuilder.builder()
                .withName("Манго")
                .build().buildInfoProductDto();
        InfoProductDto infoProductDto2 = TestDataBuilder.builder()
                .withName("Апельсин")
                .build().buildInfoProductDto();
        List<Product> productList = List.of(product, product2);
        List<InfoProductDto> infoProductDtoList = List.of(infoProductDto, infoProductDto2);
        when(repository.findAll())
                .thenReturn(productList);
        when(mapper.toInfoProductDto(product))
                .thenReturn(infoProductDto);
        when(mapper.toInfoProductDto(product2))
                .thenReturn(infoProductDto2);

        //when
        List<InfoProductDto> actualList = productService.getAll();

        //then
        assertEquals(infoProductDtoList, actualList);

    }

    @Test
    void createShouldReturnUUID() {
        //given
        ProductDto productDto = TestDataBuilder.builder()
                .build().buildProductDto();
        Product product = TestDataBuilder.builder()
                .build().buildProduct();
        when(mapper.toProduct(productDto))
                .thenReturn(product);
        when(repository.save(product))
                .thenReturn(product);
        UUID uuid = product.getUuid();

        //when
        UUID actual = productService.create(productDto);

        //then
        assertEquals(uuid, actual);
    }

    @Test
    void updateShouldInvokeOneTimes() {
        //given
        Product product = TestDataBuilder.builder()
                .build().buildProduct();
        ProductDto productDto = TestDataBuilder.builder()
                .build().buildProductDto();
        UUID uuid = product.getUuid();
        when(repository.findById(uuid))
                .thenReturn(Optional.of(product));
        when(repository.save(product))
                .thenReturn(product);
        when(mapper.merge(product,productDto))
                .thenReturn(product);

        //when
        productService.update(uuid, productDto);

        //then
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteShouldInvokeOneTimes() {
        //given
        Product product = TestDataBuilder.builder()
                .build().buildProduct();
        UUID uuid = product.getUuid();

        //when
        productService.delete(uuid);

        //then
        verify(repository, times(1)).delete(any());
    }
}
