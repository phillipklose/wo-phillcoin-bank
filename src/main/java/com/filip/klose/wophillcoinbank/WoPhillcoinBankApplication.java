package com.filip.klose.wophillcoinbank;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WoPhillcoinBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(WoPhillcoinBankApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public SimpleMailMessage templateAccountSaldo() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Hi %s!\nYour account saldo is: %s\n\nWith Regards\nWo-Phillcoin-Bank");
        return message;
    }
}
