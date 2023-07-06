package ru.sber.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.sber.entities.*;
import ru.sber.exceptions.PaymentException;
import ru.sber.proxies.BankAppProxy;
import ru.sber.repositories.CartRepository;
import ru.sber.repositories.ClientRepository;
import ru.sber.repositories.ProductCartRepository;
import ru.sber.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ProductCartRepository productCartRepository;
    private final BankAppProxy bankAppProxy;

    public CartServiceImpl(
            CartRepository cartRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository,
            ProductCartRepository productCartRepository,
            BankAppProxy bankAppProxy) {
        this.cartRepository = cartRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.productCartRepository = productCartRepository;
        this.bankAppProxy = bankAppProxy;
    }

    @Override
    public void addProductById(long cartId, long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<Cart> cartOptional = cartRepository.findById(cartId);

        if (productOptional.isPresent() && cartOptional.isPresent()) {
            Product product = productOptional.get();
            Cart cart = cartOptional.get();

            Optional<ProductCart> existingProductCart = productCartRepository.findByProductIdAndCartId(productId, cartId);

            ProductCart productCart;

            if (existingProductCart.isPresent()) {
                productCart = existingProductCart.get();
                productCart.setQuantity(productCart.getQuantity() + 1);
            } else {
                productCart = new ProductCart();
                productCart.setProduct(product);
                productCart.setCart(cart);
                productCart.setQuantity(1);
            }
            productCartRepository.save(productCart);
        } else if (cartOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Корзина не найдена");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден");
        }
    }


    @Override
    public boolean changeQuantity(long cartId, long productId, long quantity) {
        Optional<ProductCart> existingProductCart = productCartRepository.findByProductIdAndCartId(productId, cartId);

        if (existingProductCart.isPresent()) {
            ProductCart productCart = existingProductCart.get();
            productCart.setQuantity(quantity);
            productCartRepository.save(productCart);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteProductFromCart(long cartId, long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (productOptional.isPresent() && cartOptional.isPresent()) {
            Product product = new Product();
            product.setId(productId);

            Cart cart = new Cart();
            cart.setId(cartId);

            productCartRepository.deleteByProductAndCart(product, cart);

            return true;
        }
        if (cartOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Корзина не найдена");
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден");
        }
    }

    @Override
    public Optional<Payment> payment(long cartId) {
        Optional<Client> clientOptional = clientRepository.findById(cartId);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            BigDecimal balance = bankAppProxy.getBalance(client.getId());

            BigDecimal totalCost = getTotalCost(client.getCart().getId());

            if (balance.compareTo(totalCost) >= 0 && checkProductAvailability(client.getCart().getId())) {

                BigDecimal newBalance = balance.subtract(totalCost);
                bankAppProxy.setBalance(client.getId(), newBalance);

                updateProductQuantity(client.getCart().getId());

                Payment payment = new Payment(totalCost, newBalance);

                return Optional.of(payment);
            }
        }

        throw new PaymentException("Оплата не прошла");
    }

    /**
     * Считает общую стоимость товара в корзине
     * @param cartId id корзины
     * @return общую стоимость товара в корзине
     */
    private BigDecimal getTotalCost(long cartId) {
        List<ProductCart> productCarts = productCartRepository.findByCartId(cartId);
        BigDecimal totalCost = BigDecimal.ZERO;

        for (ProductCart productCart : productCarts) {
            Product product = productCart.getProduct();
            BigDecimal productPrice = product.getPrice();
            long productQuantity = productCart.getQuantity();

            BigDecimal productTotalCost = productPrice.multiply(BigDecimal.valueOf(productQuantity));
            totalCost = totalCost.add(productTotalCost);
        }

        return totalCost;
    }

    /**
     * Проверяет достаточно ли количество товара на складе
     * @param cartId id корзины
     * @return true, если достаточное количество товара, иначе false
     */
    private boolean checkProductAvailability(long cartId) {
        List<ProductCart> productCarts = productCartRepository.findByCartId(cartId);

        for (ProductCart productCart : productCarts) {
            Product product = productCart.getProduct();
            long productQuantity = productCart.getQuantity();

            if (product.getQuantity() < productQuantity) {
                return false;
            }
        }

        return true;
    }

    /**
     * Обновляет количество товара на складе после успешной покупки
     * @param cartId id корзины
     */
    private void updateProductQuantity(long cartId) {
        List<ProductCart> productCarts = productCartRepository.findByCartId(cartId);

        for (ProductCart productCart : productCarts) {
            Product product = productCart.getProduct();
            long productQuantity = productCart.getQuantity();

            long updatedQuantity = product.getQuantity() - productQuantity;
            product.setQuantity(updatedQuantity);
            productRepository.save(product);
        }
    }

    @Override
    public List<Product> getCartById(long cartId) {
        Optional<Client> clientOptional = clientRepository.findById(cartId);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            List<ProductCart> productCarts = productCartRepository.findByCartId(client.getCart().getId());
            List<Product> products = new ArrayList<>();

            for (ProductCart productCart : productCarts) {
                Product product = productCart.getProduct();
                product.setQuantity(productCart.getQuantity());
                products.add(product);
            }

            return products;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Корзина не найдена");
        }
    }
}