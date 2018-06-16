package com.filip.klose.wophillcoinbank.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.CashNotValidException;
import com.filip.klose.wophillcoinbank.runtime.exception.CashTransferException;
import com.filip.klose.wophillcoinbank.runtime.exception.GetCashException;
import com.filip.klose.wophillcoinbank.runtime.exception.UserNotFoundException;

@RunWith(SpringRunner.class)
public class BankServiceTest {

    @TestConfiguration
    static class BankServiceTestContextConfiguration {

        @Bean
        public BankService bankService() {
            return new BankService();
        }
    }

    private static final String accountToTransferFrom = "xyz_from";
    private static final String accountToTransferTo = "xyz_to";

    @Autowired
    private BankService bankService;

    @MockBean
    private UserService userService;

    @MockBean
    private CashOutOfBankService cashOutOfBankService;

    @Mock
    private User userFrom, userTo;

    @Before
    public void setup() {
        when(userService.getUserByUserId(accountToTransferFrom)).thenReturn(Optional.of(userFrom));
        when(userService.getUserByUserId(accountToTransferTo)).thenReturn(Optional.of(userTo));
    }

    @Test(expected = CashTransferException.class)
    public void transferBetweenAccountsIfNoUserFromThenThrowException() throws CashTransferException, GetCashException {
        // given
        int amount = 100;
        TransferBetweenAccountsDto betweenAccountsDto = new TransferBetweenAccountsDto("noUserFrom", accountToTransferTo, amount);

        // when
        bankService.transferBeetwenAccounts(betweenAccountsDto);
    }

    @Test(expected = CashTransferException.class)
    public void transferBetweenAccountsIfNoUserToThenThrowException() throws CashTransferException, GetCashException {
        // given
        int amount = 100;
        TransferBetweenAccountsDto betweenAccountsDto = new TransferBetweenAccountsDto(accountToTransferFrom, "noUserTo", amount);

        // when
        bankService.transferBeetwenAccounts(betweenAccountsDto);
    }

    @Test(expected = CashTransferException.class)
    public void transferBetweenAccountsIfUserFromHasNotEnoughSaldoThenThrowException() throws CashTransferException, GetCashException {
        // given
        int amount = 100;
        TransferBetweenAccountsDto betweenAccountsDto = new TransferBetweenAccountsDto(accountToTransferFrom, "noUserTo", amount);
        when(userFrom.getSaldo()).thenReturn(0);

        // when
        bankService.transferBeetwenAccounts(betweenAccountsDto);
    }

    @Test(expected = UserNotFoundException.class)
    public void getCashForNotExistingUserShouldThrowUserNotFoundException() throws UserNotFoundException, GetCashException {
        // given
        int amount = 100;
        String notExistingUserId = "notExistingUserId";
        when(userService.getUserByUserId(notExistingUserId)).thenReturn(Optional.empty());

        // when
        bankService.getCash(notExistingUserId, amount);
    }

    @Test(expected = GetCashException.class)
    public void getCashForExistingUserWithNotEnoughMoneyShouldThrowGetCashException() throws UserNotFoundException, GetCashException {
        // given
        int amount = 100;
        String existingUserId = "existingUserId";
        User user = new User();
        user.setSaldo(0);
        when(userService.getUserByUserId(existingUserId)).thenReturn(Optional.of(user));

        // when
        bankService.getCash(existingUserId, amount);
    }

    @Test(expected = UserNotFoundException.class)
    public void putCashForNotExistingUserShouldThrowUserNotFoundException() throws UserNotFoundException, CashNotValidException {
        // given
        String notExistingUserId = "notExistingUserId";
        String existingCashId = "existingCashId";

        when(userService.getUserByUserId(notExistingUserId)).thenReturn(Optional.empty());

        // when
        bankService.putCash(notExistingUserId, existingCashId);
    }

    @Test(expected = CashNotValidException.class)
    public void putCashForNotExistingCashIdShouldThrowCashNotValidException() throws UserNotFoundException, CashNotValidException {
        // given
        String existingUserId = "existingUserId";
        String notExistingCashId = "notExistingCashId";

        User user = mock(User.class);

        when(userService.getUserByUserId(existingUserId)).thenReturn(Optional.of(user));
        when(cashOutOfBankService.getCashOutOfBank(notExistingCashId)).thenReturn(Optional.empty());

        // when
        bankService.putCash(existingUserId, notExistingCashId);
    }

}