package com.filip.klose.wophillcoinbank.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.service.EmailService;
import com.filip.klose.wophillcoinbank.service.UserService;

@Component
public class ScheduledTask {

    public static final int SALARY = 5000;
    public static final int MAINTENANCE_FEE = 20;
    public static final int PAYMENT_INTERVAL = 20 * 60 * 1000;
    public static final int SALDO_STATUS_INTERVAL = 20 * 60 * 1000;
    public static final int MAINTENANCE_FEE_INTERVAL = 10 * 60 * 1000;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleMailMessage template;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = PAYMENT_INTERVAL)
    public void employerTransfersPayment() {
        paySalary(userService.getAllUsers());
    }

    @Scheduled(fixedRate = MAINTENANCE_FEE_INTERVAL)
    public void accountMaintenanceFee() {
        payFee(userService.getAllUsers());
    }

    @Scheduled(fixedRate = SALDO_STATUS_INTERVAL)
    public void saldoStatusInterval() {
        sendSaldoStatus(userService.getAllUsers());
    }

    private void paySalary(List<User> users) {
        for (User user : users) {
            user.applySalary(SALARY);
            userService.saveUser(user);
        }
    }

    private void payFee(List<User> users) {
        for (User user : users) {
            user.applyFee(MAINTENANCE_FEE);
            userService.saveUser(user);
        }
    }

    private void sendSaldoStatus(List<User> users) {
        for (User user : users) {
            String text = String.format(template.getText(), user.getFirstName(), user.getSaldo());
            emailService.sendAccountSaldoMessage(user.getEmail(), "Wo-Phillcoin-Bank Saldo Status", text);
        }
    }

}
