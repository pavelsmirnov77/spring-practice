package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
