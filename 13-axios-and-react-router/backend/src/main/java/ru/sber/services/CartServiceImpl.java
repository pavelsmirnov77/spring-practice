package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.Cart;
import ru.sber.entities.Product;
import ru.sber.entities.User;
import ru.sber.repositories.CartRepository;
import ru.sber.repositories.ProductRepository;
import ru.sber.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с корзиной пользователя
 */
@Service
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean addToCart(long userId, long productId) {
        Optional<Cart> cart = cartRepository.findCartByProduct_IdAndClient_Id(productId, userId);

        if (cart.isPresent()) {
            Cart shoppingCart = cart.get();
            shoppingCart.setQuantity(shoppingCart.getQuantity() + 1);
            cartRepository.save(shoppingCart);
            return true;
        } else {
            Optional<User> user = userRepository.findById(userId);
            Optional<Product> product = productRepository.findById(productId);

            if (user.isPresent() && product.isPresent()) {
                Cart newCart = new Cart();
                newCart.setClient(user.get());
                newCart.setProduct(product.get());
                newCart.setQuantity(1);
                cartRepository.save(newCart);
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean updateProductAmount(long userId, long productId, int amount) {
        Optional<Cart> cart = cartRepository.findCartByProduct_IdAndClient_Id(productId, userId);
        if (cart.isPresent()) {
            Cart shoppingCart = cart.get();
            shoppingCart.setQuantity(amount);
            cartRepository.save(shoppingCart);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteProduct(long userId, long productId) {
        Optional<Cart> cart = cartRepository.findCartByProduct_IdAndClient_Id(productId, userId);
        if (cart.isPresent()) {
            long idCart = cart.get().getId();
            cartRepository.deleteById(idCart);
            return true;
        }

        return false;
    }

    @Override
    public void clearCart(long userId) {
        cartRepository.deleteCartByClientId(userId);
    }

    @Override
    public List<Product> getListOfProductsInCart(long userId) {
        List<Cart> carts = cartRepository.findCartByClient_Id(userId);
        List<Product> productsInCart = new ArrayList<>();
        for (Cart cart : carts) {
            Product product = new Product();
            product.setId(cart.getProduct().getId());
            product.setName(cart.getProduct().getName());
            product.setPrice(cart.getProduct().getPrice());
            product.setQuantity(cart.getQuantity());
            productsInCart.add(product);
        }

        return productsInCart;
    }

    @Override
    public int countProductsInCart(long userId) {
        return cartRepository.countCartsByClient_Id(userId);
    }
}
