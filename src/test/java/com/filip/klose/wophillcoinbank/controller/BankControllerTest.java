package com.filip.klose.wophillcoinbank.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filip.klose.wophillcoinbank.WoPhillcoinBankApplication;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WoPhillcoinBankApplication.class)
@WebAppConfiguration
public class BankControllerTest {

    private static final String accountToTransferFrom = "xyz_from";
    private static final String accountToTransferTo = "xyz_to";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private User userFrom, userTo;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        when(userService.getUserByUserId(accountToTransferFrom)).thenReturn(Optional.of(userFrom));
        when(userService.getUserByUserId(accountToTransferTo)).thenReturn(Optional.of(userTo));
    }

    @Test
    public void transferBetweenAccountsIfAllValuesAreOkThenShouldReturnOk() throws Exception {
        // given
        int amount = 100;
        TransferBetweenAccountsDto betweenAccountsDto = new TransferBetweenAccountsDto(accountToTransferFrom, accountToTransferTo, amount);
        when(userFrom.getSaldo()).thenReturn(200);
        when(userTo.getSaldo()).thenReturn(0);

        // when & then
        mockMvc.perform(post("/bank/transfer/").content(mapper.writeValueAsString(betweenAccountsDto))
                .contentType(contentType))
                .andExpect(status().isOk());

    }

    @Test
    public void transferBetweenAccountsIfFromSaldoIsLessThenAmountThenShouldReturnBadRequst() throws Exception {
        // given
        int amount = 100;
        TransferBetweenAccountsDto betweenAccountsDto = new TransferBetweenAccountsDto(accountToTransferFrom, accountToTransferTo, amount);
        when(userFrom.getSaldo()).thenReturn(0);
        when(userTo.getSaldo()).thenReturn(0);

        // when & then
        mockMvc.perform(post("/bank/transfer/").content(mapper.writeValueAsString(betweenAccountsDto))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void transferBetweenAccountsIfFromUserDoesNotExistsThenShouldReturnBadRequst() throws Exception {
        // given
        int amount = 100;
        TransferBetweenAccountsDto betweenAccountsDto = new TransferBetweenAccountsDto("notExistingFrom", accountToTransferTo, amount);

        // when & then
        mockMvc.perform(post("/bank/transfer/").content(mapper.writeValueAsString(betweenAccountsDto))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void transferBetweenAccountsIfToUserDoesNotExistsThenShouldReturnBadRequst() throws Exception {
        // given
        int amount = 100;
        TransferBetweenAccountsDto betweenAccountsDto = new TransferBetweenAccountsDto(accountToTransferFrom, "notExistingTo", amount);

        // when & then
        mockMvc.perform(post("/bank/transfer/").content(mapper.writeValueAsString(betweenAccountsDto))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

}
