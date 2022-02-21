package ru.kulikov.saula.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kulikov.saula.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
