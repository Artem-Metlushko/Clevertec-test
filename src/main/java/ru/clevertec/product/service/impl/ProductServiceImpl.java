package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;
import ru.clevertec.product.validation.Validate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;
    private final Validate validate;

    @Override
    public InfoProductDto get(UUID uuid) {
        return productRepository.findById(uuid)
                .map(mapper::toInfoProductDto)
                .orElseThrow();
    }

    @Override
    public List<InfoProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(mapper::toInfoProductDto)
                .toList();
    }

    @Override
    public UUID create(ProductDto productDto) {
        return Optional.of(productDto)
                .filter(dto -> !validate.validateProductDto(dto))
                .map(mapper::toProduct)
                .map(productRepository::save)
                .map(Product::getUuid).orElseThrow();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        productRepository.findById(uuid)
                .map(p-> mapper.merge(p,productDto))
                .ifPresent(productRepository::save);
    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(uuid);
    }
}
