package ru.clevertec.product.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map;
import java.util.ArrayList;

@RequiredArgsConstructor
public class InMemoryProductRepository implements ProductRepository {

    private final Map<UUID, Product> products;

    @Override
    public Optional<Product> findById(UUID uuid) {

        return products.values().stream().findFirst();

    }

    @Override
    public List<Product> findAll() {

        return new ArrayList<>(products.values());
    }

    @Override
    public Product save(Product product) {
        product.setUuid(UUID.randomUUID());
        products.put(product.getUuid(), product);

        return product;
    }

    @Override
    public void delete(UUID uuid) {

        products.remove(uuid);
    }
}
