package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
