package ru.clevertec.product.validation;


import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.exception.ValidationException;

import java.math.BigDecimal;


public class Validate {

    public boolean validateProductDto(ProductDto productDto) {

        boolean result = productDto.name() == null || productDto.name().isEmpty() || productDto.name().length() < 5 || productDto.name().length() > 10
                && productDto.description().length() < 10 || productDto.description().length() > 30
                && productDto.price() != null && productDto.price().compareTo(BigDecimal.ZERO) > 0;

        if(!result){
            throw new ValidationException("Валидация не пройдена");
        }

        return true;
    }

}
