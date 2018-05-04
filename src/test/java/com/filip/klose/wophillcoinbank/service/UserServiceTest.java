package com.filip.klose.wophillcoinbank.service;

import static org.junit.Assert.*;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.filip.klose.wophillcoinbank.builder.UserBuilder;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.LoginCredentialsDto;
import com.filip.klose.wophillcoinbank.repository.UserRepository;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User testUser, testUserWithoutId;

    @Before
    public void setup() {
        testUserWithoutId = new UserBuilder().setLogin("testLogin").setPassword("password").setFirstName("testFirstName")
                .setLastName("testLastName").setEmail("testEmail").setSaldo(0).build();
        testUser = new UserBuilder().setLogin("testLogin").setPassword("password").setFirstName("testFirstName")
                .setLastName("testLastName").setEmail("testEmail").setSaldo(0).build();
        testUser.setId(new ObjectId());

        Mockito.when(userRepository.findByLoginAndPasswordOrEmailAndPassword("testLogin", "password",
                "testLogin", "password")).thenReturn(Optional.of(testUser));

        Mockito.when(userRepository.findByLoginAndPasswordOrEmailAndPassword("testEmail", "password",
                "testEmail", "password")).thenReturn(Optional.of(testUser));

        Mockito.when(userRepository.save(testUserWithoutId)).thenReturn(testUser);
    }

    @Test
    public void getUserByProperLoginCredentialsWhereLoginIsUsed() {
        LoginCredentialsDto credentials = new LoginCredentialsDto("testLogin", "password");

        final Optional<User> userByLoginCredentials = userService.getUserByLoginCredentials(credentials);

        assertTrue(userByLoginCredentials.isPresent());
        assertNotNull(userByLoginCredentials.get().getId());
        assertEquals(userByLoginCredentials.get().getLogin(), testUser.getLogin());
        assertEquals(userByLoginCredentials.get().getPassword(), testUser.getPassword());
        assertEquals(userByLoginCredentials.get().getFirstName(), testUser.getFirstName());
        assertEquals(userByLoginCredentials.get().getLastName(), testUser.getLastName());
        assertEquals(userByLoginCredentials.get().getEmail(), testUser.getEmail());
        assertEquals(userByLoginCredentials.get().getSaldo(), testUser.getSaldo());
    }

    @Test
    public void getUserByProperLoginCredentialsWhereEmailIsUsed() {
        LoginCredentialsDto credentials = new LoginCredentialsDto("testEmail", "password");

        final Optional<User> userByLoginCredentials = userService.getUserByLoginCredentials(credentials);

        assertTrue(userByLoginCredentials.isPresent());
        assertNotNull(userByLoginCredentials.get().getId());
        assertEquals(userByLoginCredentials.get().getLogin(), testUser.getLogin());
        assertEquals(userByLoginCredentials.get().getPassword(), testUser.getPassword());
        assertEquals(userByLoginCredentials.get().getFirstName(), testUser.getFirstName());
        assertEquals(userByLoginCredentials.get().getLastName(), testUser.getLastName());
        assertEquals(userByLoginCredentials.get().getEmail(), testUser.getEmail());
        assertEquals(userByLoginCredentials.get().getSaldo(), testUser.getSaldo());
    }

    @Test
    public void getUserByInProperLoginCredentialsWhereWrongLoginIsUsed() {
        LoginCredentialsDto credentials = new LoginCredentialsDto("wrongLogin", "password");

        final Optional<User> userByLoginCredentials = userService.getUserByLoginCredentials(credentials);

        assertFalse(userByLoginCredentials.isPresent());
    }

    @Test
    public void getUserByInProperLoginCredentialsWhereWrongPasswordIsUsed() {
        LoginCredentialsDto credentials = new LoginCredentialsDto("testLogin", "wrongPassword");

        final Optional<User> userByLoginCredentials = userService.getUserByLoginCredentials(credentials);

        assertFalse(userByLoginCredentials.isPresent());
    }

    @Test
    public void getUserByInProperLoginCredentialsWhereWrongLoginAndPasswordAreUsed() {
        LoginCredentialsDto credentials = new LoginCredentialsDto("wrongLogin", "wrongPassword");

        final Optional<User> userByLoginCredentials = userService.getUserByLoginCredentials(credentials);

        assertFalse(userByLoginCredentials.isPresent());
    }

    @Test
    public void saveUser() {
        final User user = userService.saveUser(testUserWithoutId);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(user.getLogin(), testUser.getLogin());
        assertEquals(user.getPassword(), testUser.getPassword());
        assertEquals(user.getFirstName(), testUser.getFirstName());
        assertEquals(user.getLastName(), testUser.getLastName());
        assertEquals(user.getEmail(), testUser.getEmail());
        assertEquals(user.getSaldo(), testUser.getSaldo());
    }
}