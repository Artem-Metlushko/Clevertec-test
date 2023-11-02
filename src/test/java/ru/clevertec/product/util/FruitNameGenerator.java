package ru.clevertec.product.util;

import java.util.Random;

public class FruitNameGenerator {
    private static final String[] FRUIT_NAMES = {"Яблоко", "Апельсин", "Банан", "Груша", "Ананас", "Манго",  "Персик", "Гранат", "Арбуз"};

    public static String generateFruitName() {
        Random random = new Random();
        int index = random.nextInt(FRUIT_NAMES.length);
        return FRUIT_NAMES[index];
    }
}

