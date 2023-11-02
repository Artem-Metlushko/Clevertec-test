package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.FruitNameGenerator;
import ru.clevertec.product.util.TestDataBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {

    private Map<UUID, Product> products =new HashMap<>();
    private InMemoryProductRepository productRepository = new InMemoryProductRepository(products);

    @Mock
    private InMemoryProductRepository mockProductRepository;

    @RepeatedTest(5)
    void findByIdShouldReturnProduct() {
        //given
        Product product = TestDataBuilder.builder()
                .withUuid(UUID.randomUUID())
                .withName(FruitNameGenerator.generateFruitName())
                .build().buildProduct();
        productRepository.save(product);

        //when
        Product actual = productRepository.findById(product.getUuid()).orElse(null);

        //then
        assertEquals(product, actual);

    }

    @Test
    void findAllShouldReturnListProducts() {
        //given
        Product product = TestDataBuilder.builder().build().buildProduct();
        Product product2 = TestDataBuilder.builder()
                .withUuid(UUID.randomUUID())
                .build().buildProduct();
        Product product3 = TestDataBuilder.builder()
                .withUuid(UUID.randomUUID())
                .build().buildProduct();
        Product product4 = TestDataBuilder.builder()
                .withUuid(UUID.randomUUID())
                .build().buildProduct();
        productRepository.save(product);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);

        //when
        List<Product> actual = productRepository.findAll();

        //then
        assertEquals(4, actual.size());

    }

    @Test
    void saveShouldReturnProductWithUUID() {
        //given
        Product product = TestDataBuilder.builder()
                .withName("Персик")
                .build().buildProduct();

        //when
        Product actual = productRepository.save(product);

        //then
        assertEquals(product, actual);
        assertNotNull(actual.getUuid());
    }


    @Test
    void deleteShouldReturnNulL() {
        //given
        Product product = TestDataBuilder.builder().build().buildProduct();

        //when
        mockProductRepository.delete(product.getUuid());

        //then
        Mockito.verify(mockProductRepository, times(1)).delete(product.getUuid());

    }
}
