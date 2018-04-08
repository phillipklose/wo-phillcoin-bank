package com.filip.klose.wophillcoinbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.repository.UserRepository;

@SpringBootApplication
public class WoPhillcoinBankApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(WoPhillcoinBankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        userRepository.save(new User("phill", "Filip", "Klose", "filip.klose@gmail.com"));
        userRepository.save(new User("marko", "Marko", "Talarko", "marko.talarko@notexistingone.com"));

        System.out.println("Users found with findAll():");
        System.out.println("-------------------------------");
        for (User user : userRepository.findAll()) {
            System.out.println(user);
        }

        System.out.println();

        System.out.println("phill found by login with findByLoginOrEmail():");
        System.out.println("-------------------------------");
        System.out.println(userRepository.findByLoginOrEmail("phill", "").orElse(null));

        System.out.println();

        System.out.println("phill found by email with findByLoginOrEmail():");
        System.out.println("-------------------------------");
        System.out.println(userRepository.findByLoginOrEmail("", "filip.klose@gmail.com").orElse(null));

        System.out.println();

        System.out.println("notExistingUser not found by email with findByLoginOrEmail():");
        System.out.println("-------------------------------");
        System.out.println(userRepository.findByLoginOrEmail("notExistingUser", "").orElse(null));
    }

}
