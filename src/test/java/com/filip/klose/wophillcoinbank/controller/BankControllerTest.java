package com.filip.klose.wophillcoinbank.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.CashOutOfBankDto;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.service.CashOutOfBankService;
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

    @MockBean
    private CashOutOfBankService cashOutOfBankService;

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

    @Test
    public void getCashForUserThatDoesNotExistShouldReturnBadRequest() throws Exception {
        // given
        String amount = "100";
        String notExistingUserId = "notExistingUserId";

        // when
        when(userService.getUserByUserId(notExistingUserId)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(get("/bank/getCash/").param("userId", notExistingUserId).param("amount", amount))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getCashForUserWithNotEnoughCashInBankShouldReturnBadRequest() throws Exception {
        // given
        String amount = "100";
        String existingUserId = "existingUserId";
        User existingUser = new User();
        existingUser.setSaldo(0);

        // when
        when(userService.getUserByUserId(existingUserId)).thenReturn(Optional.of(existingUser));

        // then
        mockMvc.perform(get("/bank/getCash/").param("userId", existingUserId).param("amount", amount))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getCashForUserWithEnoughCashInBankShouldReturnOkAndJsonWithMoney() throws Exception {
        // given
        String amount = "100";
        String existingUserId = "existingUserId";
        User existingUser = new User();
        existingUser.setSaldo(1000);

        // when
        when(userService.getUserByUserId(existingUserId)).thenReturn(Optional.of(existingUser));

        // then
        mockMvc.perform(get("/bank/getCash/").param("userId", existingUserId).param("amount", amount))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("amount").value(amount));
    }

    @Test
    public void putCashForNotExistingUserShouldReturnBadRequest() throws Exception {
        // given
        String notExistingUserId = "notExistingUserId";
        String existingCashId = "existingCashId";
        CashOutOfBankDto cashOutOfBankDto = new CashOutOfBankDto();
        cashOutOfBankDto.setId(existingCashId);
        cashOutOfBankDto.setAmount(100);

        // when
        when(userService.getUserByUserId(notExistingUserId)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(post("/bank/putCash/").param("userId", notExistingUserId)
                .content(mapper.writeValueAsString(cashOutOfBankDto)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putCashForNotExistingCashIdShouldReturnBadRequest() throws Exception {
        // given
        String existingUserId = "existingUserId";
        String notExistingCashId = "notExistingCashId";

        CashOutOfBankDto cashOutOfBankDto = new CashOutOfBankDto();
        cashOutOfBankDto.setId(notExistingCashId);
        cashOutOfBankDto.setAmount(100);

        User user = mock(User.class);

        // when
        when(userService.getUserByUserId(existingUserId)).thenReturn(Optional.of(user));
        when(cashOutOfBankService.getCashOutOfBank(notExistingCashId)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(post("/bank/putCash/").param("userId", existingUserId)
                .content(mapper.writeValueAsString(cashOutOfBankDto)).contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putCashShouldReturnOk() throws Exception {
        // given
        String existingUserId = "existingUserId";
        String existingCashId = "existingCashId";

        CashOutOfBankDto cashOutOfBankDto = new CashOutOfBankDto();
        cashOutOfBankDto.setId(existingCashId);
        cashOutOfBankDto.setAmount(100);

        User user = mock(User.class);
        CashOutOfBank cash = mock(CashOutOfBank.class);

        // when
        when(userService.getUserByUserId(existingUserId)).thenReturn(Optional.of(user));
        when(cashOutOfBankService.getCashOutOfBank(existingCashId)).thenReturn(Optional.of(cash));

        // then
        mockMvc.perform(post("/bank/putCash/").param("userId", existingUserId)
                .content(mapper.writeValueAsString(cashOutOfBankDto)).contentType(contentType))
                .andExpect(status().isOk());
    }

}
