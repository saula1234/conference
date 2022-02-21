package ru.kulikov.saula.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kulikov.saula.entity.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Integer> {
}
