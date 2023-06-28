package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;
import ru.sber.entities.Client;
import ru.sber.entities.Product;
import ru.sber.proxies.BankAppProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий, выполняющий действия над товарами интернет магазина
 */
@Repository
public class LocalCartRepository implements CartRepository {

    private final LocalProductRepository localProductRepository;

    private final BankAppProxy bankApp;
    private List<Cart> carts;

    private List<Client> clients;

    @Autowired
    public LocalCartRepository(LocalProductRepository localProductRepository, BankAppProxy bankApp, List<Client> clients) {
        this.localProductRepository = localProductRepository;
        this.carts = new ArrayList<>(List.of(
                new Cart(0, new ArrayList<>(List.of(
                        localProductRepository.getProductById(3),
                        localProductRepository.getProductById(2)
                )), "P282X"),
                new Cart(1, new ArrayList<>(List.of(
                        localProductRepository.getProductById(5),
                        localProductRepository.getProductById(4)
                )), "F829P")
        ));
        this.clients = new ArrayList<>(List.of(
                new Client(0, "Павел", "pavelsmir", "89uip12", "pavel@yandex.ru", carts.get(1))
        ));
        this.bankApp = bankApp;
    }

    /**
     * Добавляет товар в корзину по id
     * @param cartId id корзины
     * @param productId id товара
     * @return true, если успешно добавлено, иначе false
     */
    @Override
    public boolean addProductById(long cartId, long productId) {
        Cart cart = carts.get((int) cartId);
        if (cart == null) {
            return false;
        }

        cart.getProducts().add(localProductRepository.getProductById(productId));
        return true;
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
        Cart cart = carts.get((int) cartId);
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
    @Override
    public boolean payment(long cartId) {
        Cart cart = carts.get((int) cartId);
        BigDecimal sum = BigDecimal.ZERO;

        if (cart == null) {
            return false;
        }

        for (Product p : cart.getProducts()) {
            sum = sum.add(p.getPrice());
        }

        Client client = getClientByCartId(cartId);
        if (client == null) {
            return false;
        }

        BigDecimal clientBalance = bankApp.getBalanceClient(client.getId());

        return sum.compareTo(clientBalance) <= 0;
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
        Cart cart = carts.get((int) cartId);
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
        return carts.get((int) cartId);
    }
}
