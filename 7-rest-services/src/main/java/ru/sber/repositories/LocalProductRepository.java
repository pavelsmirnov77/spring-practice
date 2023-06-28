package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.entities.Product;

import java.math.BigDecimal;
import java.util.*;

/**
 * Репозиторий, выполняющий действия над товаром
 */
@Repository
public class LocalProductRepository implements ProductRepository {
    private List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Термокружка", BigDecimal.valueOf(350), 100),
            new Product(2, "Гантели", BigDecimal.valueOf(1200), 50),
            new Product(3, "Игровая мышка", BigDecimal.valueOf(2500), 30),
            new Product(4, "Настольная лампа", BigDecimal.valueOf(900), 37),
            new Product(5, "Комплект ручек", BigDecimal.valueOf(250), 50),
            new Product(6, "Игровая клавиатура", BigDecimal.valueOf(2900), 100),
            new Product(7, "Игрушка антистресс", BigDecimal.valueOf(570), 40)
    ));

    /**
     * Добавляет товар
     * @param product объект товара
     * @return id товара
     */
    @Override
    public long createProduct(Product product) {
        long id = generateId();
        product.setId(id);
        products.add(product);

        return id;
    }

    /**
     * Изменяет название и цену товара
     * @param product объект товара
     * @return true, если товар изменен улачно, иначе false
     */
    @Override
    public boolean changeProduct(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                p.setName(product.getName());
                p.setPrice(product.getPrice());

                return true;
            }
        }
        return false;
    }

    /**
     * Удаляет товар по id
     * @param productId id товара
     * @return true, если товар удален успешно, иначе false
     */
    @Override
    public boolean deleteProductById(long productId) {
        return products.removeIf(product -> product.getId() == productId);
    }

    /**
     * Ищет товары по названию
     * @param productName название товара
     * @return список товаров с заданным названием
     */
    @Override
    public List<Product> findProductByName(String productName) {
        if (productName == null) {
            return products;
        }

        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .toList();
    }

    /**
     * Получение товара по id
     * @param productId id товара
     * @return объект полученного товара
     */
    @Override
    public Product getProductById(long productId) {
        return products.get((int) productId);
    }

    /**
     * Генерирует случайный id
     * @return случайное число
     */
    private long generateId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}
