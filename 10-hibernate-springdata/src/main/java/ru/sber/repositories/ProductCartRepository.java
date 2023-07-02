package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;
import ru.sber.entities.Product;
import ru.sber.entities.ProductCart;

import java.util.List;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {
    void deleteByProduct(Product product);

    @Query("SELECT pc FROM ProductCart pc WHERE pc.cart.id = :cartId")
    List<ProductCart> findByCartId(long cartId);

    @Query("SELECT pc FROM ProductCart pc WHERE pc.product.id = :productId AND pc.cart.id = :cartId")
    ProductCart findByProductIdAndCartId(@Param("productId") long productId, @Param("cartId") long cartId);

    void deleteByProductAndCart(Product product, Cart cart);
}
