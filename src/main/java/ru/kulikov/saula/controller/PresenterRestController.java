package ru.kulikov.saula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kulikov.saula.entity.Presentation;
import ru.kulikov.saula.entity.Room;
import ru.kulikov.saula.entity.Schedule;
import ru.kulikov.saula.entity.User;
import ru.kulikov.saula.service.PresentationService;
import ru.kulikov.saula.service.RoomService;
import ru.kulikov.saula.service.ScheduleService;
import ru.kulikov.saula.service.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/presenter")
public class PresenterRestController {

    @Autowired
    UserService userService;

    @Autowired
    PresentationService presentationService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RoomService roomService;

    //Выдача имени залогиневшегося пользователя
    private String getCurrentUsername(){
        String ActiveUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        ActiveUserName = ActiveUserName.substring(ActiveUserName.lastIndexOf("[", 79) + 10);
        ActiveUserName =  ActiveUserName.substring(0,ActiveUserName.lastIndexOf(",", 12));

        return ActiveUserName;
    }

    private void addPresentation(Schedule schedule, User tempUser){

        boolean check = false;

        Set<Presentation> presentations = tempUser.getPresentations();

        for(Presentation pres : presentations){
            if(pres.getId()==schedule.getPresentationId())
                check = true;
        }

        if(!check)
            throw new RuntimeException("Presentation with id " + schedule.getPresentationId() + " does not exist");

        int[] timeArray = new int[]{10, 12, 14, 16, 18, 20};
        ArrayList<Room> roomList = (ArrayList<Room>) roomService.findAll();
        ArrayList<Schedule> schedules = (ArrayList<Schedule>) scheduleService.findAll();

        boolean timeCheck = false;
        boolean RoomCheck = false;
        boolean ScheduleCollision = false;

        for (Room room : roomList) {
            if (room.getId() == schedule.getRoomId())
                RoomCheck = true;
        }

        if (!RoomCheck)
            throw new RuntimeException("Incorrect room_id value - " + schedule.getRoomId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //По задумке презентации проходят каждые два часа каждый день с 10:00:00 до 20:00:00 на другое время нельзя ставить презентации
        for (int i : timeArray) {
            if ((i + ":00:00").equals(dateFormat.format(schedule.getTime()).substring(11)))
                timeCheck = true;
        }

        if (!timeCheck)
            throw new RuntimeException("Incorrect time value - " + dateFormat.format(schedule.getTime()).substring(11));

        for (Schedule sched : schedules) {
            if ((sched.getRoomId() == schedule.getRoomId()) && (dateFormat.format(sched.getTime()).equals(dateFormat.format(schedule.getTime()))))
                ScheduleCollision = true;
        }

        if (ScheduleCollision)
            throw new RuntimeException("Presentation in this room and time already exists");

        scheduleService.save(schedule);
    }
    //Выдача презентаций пользователя
    @GetMapping("/") //http://localhost:8090/presenter/
    private Set<Presentation> getPresentations(){

        User tempUser = userService.findByUsername(getCurrentUsername());

        Set<Presentation> presentations = tempUser.getPresentations();

        return presentations;
    }

    //Добавление новой презентации
    @PostMapping("/presentation/{presentationName}") //http://localhost:8090/presenter/presentation/
    private void addNewPresentation(@PathVariable String presentationName, @RequestBody Schedule schedule){

        Presentation presentation = new Presentation();

        presentation.setTitle(presentationName);

        presentationService.save(presentation);

        User tempUser = userService.findByUsername(getCurrentUsername());

        tempUser.getPresentations().add(presentation);

        userService.save(tempUser);

        Set<Presentation> presentationList = tempUser.getPresentations();
        List<Integer> list = new ArrayList<>();
        for(Presentation pres : presentationList){
            list.add(pres.getId());
        }

        schedule.setPresentationId(Collections.max(list));

        addPresentation(schedule, tempUser);

    }

    //Добавление существующей презентации пользователю
    @PutMapping("/presentation/{presentationId}") //http://localhost:8090/presenter/presentation/
    private  void addExistingPresentation(@PathVariable int presentationId){

        Presentation presentation = presentationService.findById(presentationId);

        User tempUser = userService.findByUsername(getCurrentUsername());

        Set<Presentation> set = tempUser.getPresentations();

        set.add(presentation);

        tempUser.setPresentations(set);

        userService.save(tempUser);

    }

    //Удаление презентации у пользователя
    //Если этой презентации не осталось у других пользователей, удалить из списка презентаций
    @DeleteMapping("/{presentationId}") //http://localhost:8090/presenter/
    private void deletePresentation(@PathVariable int presentationId){

        Presentation presentation = presentationService.findById(presentationId);

        User tempUser = userService.findByUsername(getCurrentUsername());

        tempUser.getPresentations().remove(presentation);

        userService.save(tempUser);

        boolean check = false;

        ArrayList<User> users = (ArrayList<User>) userService.findAll();

        for(User user : users){
            Set<Presentation> presentations = user.getPresentations();
            for(Presentation presentation1 : presentations){
                if(presentation1.getId()==presentationId){
                    check = true;
                }
            }
        }
        //Одновременно с удалением презентации нужно удалять строку в расписании
        if(!check){

            Schedule tempSchedule = scheduleService.findByIdForPresentation(presentationId);
            if(tempSchedule!=null){
                scheduleService.deleteById(presentationId);
            }
            presentationService.deleteById(presentationId);
        }

    }

    //Добавление/изменение существующей презентации в расписание
    @PostMapping("/schedule") //http://localhost:8090/presenter/schedule
    public Schedule addSchedule(@RequestBody Schedule schedule) {

        User tempUser = userService.findByUsername(getCurrentUsername());

        addPresentation(schedule, tempUser);

        return schedule;

    }

}
