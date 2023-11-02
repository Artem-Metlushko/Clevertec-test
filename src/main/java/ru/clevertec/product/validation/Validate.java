package ru.clevertec.product.validation;

<<<<<<< HEAD

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

=======
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;

public class Validate {
    public static boolean validateProduct(Product product) {
        if (product.getUuid() == null) {
            return false;
        }
        if (product.getName() == null || product.getName().isEmpty() || product.getName().length() < 5 || product.getName().length() > 10) {
            return false;
        }
        if (product.getDescription() != null && (product.getDescription().length() < 10 || product.getDescription().length() > 30)) {
            return false;
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return product.getCreated() != null;
    }

    public static boolean validateInfoProductDto(InfoProductDto infoProductDto) {
        if (infoProductDto.uuid() == null) {
            return false;
        }
        if (infoProductDto.name() == null || infoProductDto.name().isEmpty() || infoProductDto.name().length() < 5 || infoProductDto.name().length() > 10) {
            return false;
        }
        if (infoProductDto.description() == null) {
            return false;
        }
        return infoProductDto.price() != null && infoProductDto.price().compareTo(BigDecimal.ZERO) > 0;
    }
>>>>>>> develop
}
