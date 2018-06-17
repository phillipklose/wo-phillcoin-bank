package com.filip.klose.wophillcoinbank.scheduler;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

import com.filip.klose.wophillcoinbank.builder.UserBuilder;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.service.*;

@RunWith(SpringRunner.class)
public class ScheduledTaskTest {

    @TestConfiguration
    static class ScheduledTaskTestContextConfiguration {

        @Bean
        public ScheduledTask scheduledTask() {
            return new ScheduledTask();
        }
    }

    @Autowired
    private ScheduledTask scheduledTask;

    @MockBean
    private UserService userService;

    @MockBean
    private SimpleMailMessage template;

    @MockBean
    private EmailService emailService;

    @MockBean
    private CyclicTransferService cyclicTransferService;

    @MockBean
    private BankService bankService;

    @MockBean
    private LoanCashService loanCashService;

    private User user1, user2;
    private int startSaldoOfUser1 = 0;
    private int startSaldoOfUser2 = 500;

    @Before
    public void setup() {
        user1 = new UserBuilder().setLogin("testLogin1").setPassword("password1").setFirstName("testFirstName1")
                .setLastName("testLastName1").setEmail("testEmail1").setSaldo(startSaldoOfUser1).build();
        user2 = new UserBuilder().setLogin("testLogin2").setPassword("password2").setFirstName("testFirstName2")
                .setLastName("testLastName2").setEmail("testEmail2").setSaldo(startSaldoOfUser2).build();

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        when(userService.saveUser(user1)).thenReturn(user1);
        when(userService.saveUser(user2)).thenReturn(user2);
    }

    @Test
    public void employerTransfersPayment() {
        // when
        scheduledTask.employerTransfersPayment();

        // then
        Assert.assertEquals(user1.getSaldo(), startSaldoOfUser1 + scheduledTask.SALARY);
        Assert.assertEquals(user2.getSaldo(), startSaldoOfUser2 + scheduledTask.SALARY);
    }

    @Test
    public void accountMaintenanceFee() {
        // when
        scheduledTask.accountMaintenanceFee();

        // then
        Assert.assertEquals(user1.getSaldo(), startSaldoOfUser1 - scheduledTask.MAINTENANCE_FEE);
        Assert.assertEquals(user2.getSaldo(), startSaldoOfUser2 - scheduledTask.MAINTENANCE_FEE);
    }
}