package com.github.uinet.project.services;

import com.github.uinet.project.domain.Role;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.exception.UserException;
import com.github.uinet.project.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private  UserService userService;

    @Mock
    private Page<User> userPage;

    @Mock
    private Pageable pageable;

    private User user;

    @Before
    public void init() {
        user = User.builder()
                .id(1l)
                .username("TestUsername")
                .name("TestName")
                .role(Collections.singleton(Role.CLIENT))
                .money(100)
                .build();
    }

    @Test
    public void loadUserByUsername() {
        String username = "TestUsername";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertEquals(user, userService.loadUserByUsername(username));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNotFound(){
        String username = ArgumentMatchers.anyString();
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        User user = userService.loadUserByUsername(username);
    }

    @SneakyThrows
    @Test(expected = UserException.class)
    public void registerNewUserAndUserAlreadyExist() {
        when(userRepository.save(user)).thenThrow();
        userService.registerNewUser(user);
    }

    @Test
    public void buyOrder() {
    }

    @Test
    public void findPaginated() {
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        assertEquals(userPage, userService.findPaginated(pageable));
    }

    @Test
    public void topUpBalance() {
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.getById(user.getId())).thenReturn(user);
        double sum = ArgumentMatchers.anyDouble();

        assertEquals(user.getMoney() + sum, userService.topUpBalance(user.getId(),sum), 0.1);
    }
}