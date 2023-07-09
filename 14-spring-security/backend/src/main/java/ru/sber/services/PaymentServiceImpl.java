package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.Payment;
import ru.sber.entities.Product;
import ru.sber.exceptions.PaymentException;
import ru.sber.exceptions.ProductsException;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.proxies.BankAppProxy;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для взаимодействия с оплатой товаров
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final BankAppProxy bankAppProxy;

    @Autowired
    public PaymentServiceImpl(CartService cartService, UserService userService,
                              ProductService productService, BankAppProxy bankAppProxy) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
        this.bankAppProxy = bankAppProxy;
    }

    @Override
    @Transactional
    public boolean pay(Payment payment) {

        long userId = payment.getUserId();
        boolean userNotFound = !userService.checkUserExistence(userId);

        if (userNotFound) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        int amountProductsInCart = cartService.countProductsInCart(userId);

        if (amountProductsInCart == 0) {
            throw new PaymentException("Корзина пуста");
        }

        List<Product> productsInCart = cartService.getListOfProductsInCart(userId);

        boolean productsNotEnough = checkProductsAmount(productsInCart);

        if (productsNotEnough) {
            throw new ProductsException("Не хватает товара на складе");
        }

        long cardNumber = payment.getCardNumber();
        BigDecimal sumOfCart = getSumOfCart(productsInCart);
        BigDecimal amountOfMoney = bankAppProxy.getBalance(cardNumber);

        if (amountOfMoney.compareTo(sumOfCart) >= 0) {
            updateAmountOfProducts(productsInCart);
            cartService.clearCart(userId);
            return true;
        }

        return false;
    }

    /**
     * Сравнивает количество товара на складе и в корзине пользователя
     *
     * @param productsInCart Список товаров в корзине
     * @return Возвращает результпт сравнения
     */
    private boolean checkProductsAmount(List<Product> productsInCart) {
        for (Product product : productsInCart) {
            if (product.getQuantity() > productService.findById(product.getId()).get().getQuantity()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Рассчитывает полную стоимость корзины
     *
     * @param productsInCart Список товаров в корзине
     * @return Возвращает полную стоимость корзины
     */
    private BigDecimal getSumOfCart(List<Product> productsInCart) {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Product product : productsInCart) {
            BigDecimal amount = BigDecimal.valueOf(product.getQuantity());
            BigDecimal price = productService.findById(product.getId()).get().getPrice();
            sum = sum.add(price.multiply(amount));
        }
        return sum;
    }

    /**
     * Изменяет количество продуктов после их продажи
     *
     * @param productsInCart Список товаров в корзине
     */
    private void updateAmountOfProducts(List<Product> productsInCart) {

        for (Product product : productsInCart) {
            Product updatedProduct = new Product();
            updatedProduct.setId(product.getId());
            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setQuantity(productService.findById(product.getId()).get().getQuantity() - product.getQuantity());
            productService.update(updatedProduct);
        }

    }
}
