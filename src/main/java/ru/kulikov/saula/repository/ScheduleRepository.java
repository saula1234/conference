package ru.kulikov.saula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kulikov.saula.entity.Schedule;

import java.util.Collection;
import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT u FROM Schedule u")
    Collection<Schedule> findAllSchedules();
}
