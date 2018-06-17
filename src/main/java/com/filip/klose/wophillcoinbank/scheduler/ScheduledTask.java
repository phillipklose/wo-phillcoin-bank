package com.filip.klose.wophillcoinbank.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.filip.klose.wophillcoinbank.entity.CyclicTransfer;
import com.filip.klose.wophillcoinbank.entity.LoanCash;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.CashTransferException;
import com.filip.klose.wophillcoinbank.runtime.exception.GetCashException;
import com.filip.klose.wophillcoinbank.service.*;

@Component
public class ScheduledTask {

    public static final int SALARY = 5000;
    public static final int MAINTENANCE_FEE = 20;
    public static final int PAYMENT_INTERVAL = 20 * 60 * 1000;
    public static final int SALDO_STATUS_INTERVAL = 20 * 60 * 1000;
    public static final int MAINTENANCE_FEE_INTERVAL = 10 * 60 * 1000;
    public static final int INTERVAL_CHECK = 1000;

    @Autowired
    private UserService userService;

    @Autowired
    private CyclicTransferService cyclicTransferService;

    @Autowired
    private SimpleMailMessage template;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BankService bankService;

    @Autowired
    private LoanCashService loanCashService;

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

    @Scheduled(fixedRate = INTERVAL_CHECK)
    public void cyclePaymentsInterval() {
        handleCyclePayments();
    }

    @Scheduled(fixedRate = INTERVAL_CHECK)
    public void loanCashInterval() {
        handleLoanCashCheck();
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

    private void handleCyclePayments() {
        final List<CyclicTransfer> allCyclicTransfers = cyclicTransferService.getAll();
        for (CyclicTransfer allCyclicTransfer : allCyclicTransfers) {
            if (allCyclicTransfer.getTransactionTime().before(new Date())) {
                TransferBetweenAccountsDto transferBetweenAccountsDto = toTransferBetweenAccountsDto(allCyclicTransfer);
                try {
                    bankService.transferBeetwenAccounts(transferBetweenAccountsDto);
                    LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(allCyclicTransfer.getIntervalInMinutes(), ChronoUnit
                            .MINUTES));
                    Date tmfn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                    allCyclicTransfer.setTransactionTime(tmfn);
                    cyclicTransferService.saveCyclicTransfer(allCyclicTransfer);
                } catch (CashTransferException | GetCashException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleLoanCashCheck() {
        final List<LoanCash> loanCashes = loanCashService.getAll();
        for (LoanCash loan : loanCashes) {
            if (loan.getTimeToPayBack().before(new Date())) {
                loan.calculateNewAmount();
                loanCashService.save(loan);
                sendLoanMessage(loan);
            }
        }
    }

    private void sendLoanMessage(LoanCash loan) {
        final User user = userService.getUserByUserId(loan.getUserId()).get();
        String text = String.format("Hi %s!\nYour loan gets bigger, current value is: %s\n\nWith Regards\nWo-Phillcoin-Bank", user
                .getFirstName(), loan.getAmount());
        emailService.sendAccountSaldoMessage(user.getEmail(), "Wo-Phillcoin-Bank Loan Status", text);
    }

    private TransferBetweenAccountsDto toTransferBetweenAccountsDto(CyclicTransfer cyclicTransfer) {
        return new TransferBetweenAccountsDto(cyclicTransfer.getFrom(), cyclicTransfer.getTo(), cyclicTransfer.getAmount());
    }

}
