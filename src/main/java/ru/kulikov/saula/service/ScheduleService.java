package ru.kulikov.saula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kulikov.saula.entity.Schedule;
import ru.kulikov.saula.exception.ResourceNotFoundException;
import ru.kulikov.saula.repository.ScheduleRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(int id) {
        Optional<Schedule> result = scheduleRepository.findById(id);

        Schedule theSchedule;

        if (result.isPresent()) {
            theSchedule = result.get();
        }
        else {
            throw new ResourceNotFoundException("Did not find schedule id - " + id);
        }

        return theSchedule;
    }

    public Schedule findByIdForPresentation(int id) {
        Optional<Schedule> result = scheduleRepository.findById(id);

        Schedule theSchedule = null;

        if (result.isPresent()) {
            theSchedule = result.get();
        }

        return theSchedule;
    }

    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void deleteById(int id) {
        scheduleRepository.deleteById(id);
    }

    public Collection<Schedule> findAllSchedules(){return scheduleRepository.findAllSchedules();}
}
