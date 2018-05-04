package com.filip.klose.wophillcoinbank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.mapper.UserMapper;
import com.filip.klose.wophillcoinbank.model.SaldoDto;
import com.filip.klose.wophillcoinbank.service.UserService;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @GetMapping("/saldo")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestParam("userId") String userId) {
        final Optional<User> user = userService.getUserByUserId(userId);
        if (user.isPresent()) {
            final SaldoDto saldoDto = mapper.convertToSaldoDto(user.get());
            return ResponseEntity.ok(saldoDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
