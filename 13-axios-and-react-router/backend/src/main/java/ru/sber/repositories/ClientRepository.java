package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
