package ru.kulikov.saula.service;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kulikov.saula.entity.User;
import ru.kulikov.saula.repository.UserRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    public void findAllTest(){
        when(repository.findAll()).thenReturn(Stream
                .of(new User(), new User()).collect(Collectors.toList()));
        assertEquals(2, service.findAll().size());
    }

    @Test
    public void findByUserNameTest(){
        String name = "Sasha";
        User user = new User();
        user.setUsername("Sasha");
        when(repository.findByUsername(name)).thenReturn(user);
        assertEquals("Sasha", service.findByUsername("Sasha").getUsername());
    }
}