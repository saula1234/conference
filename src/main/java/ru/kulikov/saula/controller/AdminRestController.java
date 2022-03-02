package ru.kulikov.saula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kulikov.saula.entity.Presentation;
import ru.kulikov.saula.entity.User;
import ru.kulikov.saula.exception.UserUpdateException;
import ru.kulikov.saula.service.PresentationService;
import ru.kulikov.saula.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private PresentationService presentationService;

    //Список всех зарегестриванных пользователей
    @GetMapping("/users") //http://localhost:8090/admin/users
    public List<User> findAll() {
        return userService.findAll();
    }

    //Добавление нового пользователя
    @PostMapping("/user") //http://localhost:8090/admin/user
    public User addUser(@RequestBody User user) {

        return userService.addUser(user);
    }

    //Удаление пользователя
    @DeleteMapping("/user/{userId}") //http://localhost:8090/admin/user/
    public String deleteUser(@PathVariable int userId) {

        User tempUser = userService.findById(userId);

        if (tempUser == null) {
            throw new RuntimeException("User id not found - " + userId);
        }

        userService.deleteById(userId);

        return "Deleted user id - " + userId;
    }

    //Повышение юзера до "Докладчика"
    @PutMapping("/user/{userId}") //http://localhost:8090/admin/user/
    public User updateUser(@PathVariable int userId) {

        User tempUser = userService.findById(userId);

        if (tempUser == null) {
            throw new RuntimeException("User id not found - " + userId);
        }

        if (tempUser.getRole().equals("ROLE_ADMIN")){
            throw new UserUpdateException("You can not update users with admin role");
        }

        if (tempUser.getRole().equals("ROLE_PRESENTER")){
            throw new UserUpdateException("This user is presenter already");
        }
        tempUser.setRole("ROLE_PRESENTER");

        userService.save(tempUser);

        return tempUser;
    }

    //Список всех существующих презентаций
    @GetMapping("/presentations") //http://localhost:8090/admin/presentations
    public List<Presentation> findAllPresentations() {
        return presentationService.findAll();
    }
}
