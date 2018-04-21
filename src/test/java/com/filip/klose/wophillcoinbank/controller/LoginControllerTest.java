package com.filip.klose.wophillcoinbank.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filip.klose.wophillcoinbank.WoPhillcoinBankApplication;
import com.filip.klose.wophillcoinbank.builder.UserBuilder;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WoPhillcoinBankApplication.class)
@WebAppConfiguration
public class LoginControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private User userThatWantToRegister, userThatWantToRegisterWithLoginAndPassword;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        userThatWantToRegister = new UserBuilder().setLogin("testLogin").setPassword("password").setFirstName("testFirstName")
                .setLastName("testLastName").setEmail("testEmail").build();

        userThatWantToRegisterWithLoginAndPassword = new UserBuilder().setLogin("testLoginForUserWithoutAllElements")
                .setPassword("testPasswordForUserWithoutAllElements").build();
    }

    @Test
    public void testRegisterUserWithAllFieldsSet() throws Exception {
        mockMvc.perform(post("/login/register/").content(mapper.writeValueAsString(userThatWantToRegister))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("login").value("testLogin"))
                .andExpect(jsonPath("firstName").value("testFirstName"))
                .andExpect(jsonPath("lastName").value("testLastName"))
                .andExpect(jsonPath("email").value("testEmail"));
    }

    @Test
    public void testRegisterUserWithLoginAndPasswordFieldsSet() throws Exception {
        mockMvc.perform(post("/login/register/").content(mapper.writeValueAsString(userThatWantToRegisterWithLoginAndPassword))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("login").value("testLoginForUserWithoutAllElements"))
                .andExpect(jsonPath("firstName").isEmpty())
                .andExpect(jsonPath("lastName").isEmpty())
                .andExpect(jsonPath("email").isEmpty());
    }

    @Test
    public void login() {
    }

    // TODO: delete users after test pass
//    @After
//    public void tearDown() {
//        userRepository.delete(userThatWantToRegister);
//        userRepository.delete(userThatWantToRegisterWithLoginAndPassword);
//    }
}