package com.filip.klose.wophillcoinbank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/raport")
public class RaportController {

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getLoggedInUser() {
        // TODO: Generate raport for all users; their account ballance, investment and debts - generated in form of a csv file.
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
