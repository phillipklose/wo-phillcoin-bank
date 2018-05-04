package com.filip.klose.wophillcoinbank.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.After;
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

import com.filip.klose.wophillcoinbank.WoPhillcoinBankApplication;
import com.filip.klose.wophillcoinbank.builder.UserBuilder;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WoPhillcoinBankApplication.class)
@WebAppConfiguration
public class InfoControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private User userWithSaldo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        userWithSaldo = new UserBuilder().setLogin("testLogin").setPassword("password").setFirstName("testFirstName")
                .setLastName("testLastName").setEmail("testEmail").setSaldo(100).build();

        userWithSaldo = userRepository.insert(userWithSaldo);
    }

    @Test
    public void testGetSaldoWhenUserExists() throws Exception {
        mockMvc.perform(get("/info/saldo/").param("userId", userWithSaldo.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("saldo").value(userWithSaldo.getSaldo()));
    }

    @Test
    public void testGetSaldoWhenUserDoesNotExists() throws Exception {
        mockMvc.perform(get("/info/saldo/").param("userId", "notExistingId"))
                .andExpect(status().isNotFound());
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }
}