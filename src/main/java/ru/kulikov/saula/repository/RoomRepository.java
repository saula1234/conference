package ru.kulikov.saula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kulikov.saula.entity.Presentation;
import ru.kulikov.saula.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
