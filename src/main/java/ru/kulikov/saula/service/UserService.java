package ru.kulikov.saula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kulikov.saula.entity.User;
import ru.kulikov.saula.exception.ResourceNotFoundException;
import ru.kulikov.saula.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        Optional<User> result = userRepository.findById(id);

        User user;

        if (result.isPresent()) {
            user = result.get();
        } else {
            //Юзер с таким id не найден
            throw new ResourceNotFoundException("Did not find user id - " + id);
        }

        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public User addUser(User user) {

        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();

        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()))
                throw new RuntimeException("User with name: " + user.getUsername() + " already exists");
        }

        user.setId(0);

        user.setRole("ROLE_USER");

        String s = user.getPassword();

        user.setPassword(bCryptPasswordEncoder.encode(s));

        userRepository.save(user);

        return user;
    }


}
