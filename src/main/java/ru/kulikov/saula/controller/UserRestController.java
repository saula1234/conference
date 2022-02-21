package ru.kulikov.saula.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kulikov.saula.entity.Schedule;
import ru.kulikov.saula.sched.ScheduleDTO;
import ru.kulikov.saula.service.PresentationService;
import ru.kulikov.saula.service.RoomService;
import ru.kulikov.saula.service.ScheduleService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {


    @Autowired
    private PresentationService presentationService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ScheduleService scheduleService;



    //Выдача расписания с понятной пользователю информацией

    @GetMapping("/") //http://localhost:8090/user/
    public List<ScheduleDTO> findAll() {

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        ArrayList<Schedule> schedules = (ArrayList<Schedule>) scheduleService.findAllSchedules();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");

        for (Schedule schedule : schedules) {
            ScheduleDTO ss = new ScheduleDTO(presentationService.findById(schedule.getPresentationId()).getTitle()
                    , roomService.findById(schedule.getRoomId()).getName(), dateFormat.format(schedule.getTime()));
            scheduleDTOS.add(ss);
        }

        return scheduleDTOS;
    }

}
