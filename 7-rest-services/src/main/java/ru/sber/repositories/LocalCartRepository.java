package ru.sber.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;
import ru.sber.entities.Client;
import ru.sber.entities.Payment;
import ru.sber.entities.Product;
import ru.sber.proxies.BankAppProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий, выполняющий действия над товарами интернет магазина
 */
@Slf4j
@Repository
public class LocalCartRepository implements CartRepository {

    private final LocalProductRepository localProductRepository;

    private final BankAppProxy bankApp;
    private List<Cart> carts;

    private List<Client> clients;

    @Autowired
    public LocalCartRepository(LocalProductRepository localProductRepository, BankAppProxy bankApp) {
        this.localProductRepository = localProductRepository;
        this.carts = new ArrayList<>(List.of(
                new Cart(1, new ArrayList<>(List.of(
                        localProductRepository.getProductById(3)
                )), "P282X"),
                new Cart(2, new ArrayList<>(List.of(
                        localProductRepository.getProductById(5)
                )), "F829P")
        ));
        this.clients = new ArrayList<>(List.of(
                new Client(1, "Павел", "pavelsmir", "89uip12", "pavel@yandex.ru", carts.get(0))
        ));
        this.bankApp = bankApp;
    }

    /**
     * Добавляет товар в корзину по id
     * @param cartId id корзины
     * @param productId id товара
     */
    @Override
    public void addProductById(long cartId, long productId) {
        Cart cart = carts.get((int) cartId - 1);
        List<Product> products = cart.getProducts();
        List<Product> existingProducts;
        boolean containsProduct = products.stream().anyMatch(p -> p.getId() == productId);

        if (containsProduct) {
            existingProducts = products.stream()
                    .filter(product -> product.getId() == productId)
                    .toList();
            for (Product p : existingProducts) {
                p.setQuantity(p.getQuantity() + 1);
            }
        } else {
            Product newProduct = localProductRepository.getProductById(productId);
            newProduct.setQuantity(1);
            products.add(newProduct);
        }
    }

    /**
     * Изменяет количество товара в корзине
     * @param cartId id корзины
     * @param productId id продукта
     * @param quantity количество товара
     * @return true, если удачно обновлено значение, иначе false
     */
    @Override
    public boolean changeQuantity(long cartId, long productId, long quantity) {
        Cart cart = carts.get((int) cartId - 1);
        if (cart == null && localProductRepository.getProductById(productId) == null) {
            return false;
        }

        Product product = localProductRepository.getProductById(productId);
        product.setQuantity(quantity);
        return true;
    }

    /**
     * Осуществляет оплату корзины
     * @param cartId id корзины
     * @return true, если оплата прошла успешно, иначе false
     */
    public Payment payment(long cartId) {
        Cart cart = carts.get((int) cartId - 1);
        BigDecimal amountBuy = BigDecimal.ZERO;

        if (cart == null) {
            return null;
        }

        for (Product p : cart.getProducts()) {
            BigDecimal productTotal = p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity()));
            amountBuy = amountBuy.add(productTotal);
        }

        Client client = getClientByCartId(cartId);
        if (client == null) {
            return null;
        }

        BigDecimal clientBalance = bankApp.getBalanceClient(client.getId());
        if (amountBuy.compareTo(clientBalance) <= 0) {
            clientBalance = clientBalance.subtract(amountBuy);
            bankApp.setBalanceClient(client.getId(), clientBalance);
            return new Payment(amountBuy, clientBalance);
        } else {
            return null;
        }
    }

    /**
     * Получает клиента по id корзины
     * @param cartId id корзины
     * @return объект клиента
     */
    private Client getClientByCartId(long cartId) {
        for (Client client : clients) {
            if (client.getCart().getId() == cartId) {
                return client;
            }
        }
        return null;
    }

    /**
     * Удаляет товар из корзины
     * @param cartId id корзины
     * @param productId id товара
     * @return true, если товар удален успешно, иначе false
     */
    @Override
    public boolean deleteProductFromCart(long cartId, long productId) {
        Cart cart = carts.get((int) cartId - 1);
        if (cart == null || localProductRepository.getProductById(productId) == null) {
            return false;
        }

        return cart.getProducts().removeIf(product -> product.getId() == productId);
    }

    /**
     * Получает корзины по ее id
     * @param cartId id корзины
     * @return объект корзины
     */
    @Override
    public Cart getCartById(long cartId) {
        return carts.get((int) cartId - 1);
    }
}
