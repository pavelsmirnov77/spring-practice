package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;
import ru.sber.entities.Client;
import ru.sber.entities.Product;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
