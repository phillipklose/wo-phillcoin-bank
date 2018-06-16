package com.filip.klose.wophillcoinbank.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.mapper.UserMapper;
import com.filip.klose.wophillcoinbank.model.UserDto;
import com.filip.klose.wophillcoinbank.service.UserService;

@RestController
@RequestMapping(value = "/raport", produces = "text/csv")
public class RaportController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getReportForAllUsers() {
        // TODO: Generate raport for all users; their account ballance, investment and debts - generated in form of a csv file.
        final List<User> users = userService.getAllUsers();
        final List<UserDto> userDtos = users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
        final CsvMapper csvMapper = new CsvMapper();
        final CsvSchema schema = csvMapper.schemaFor(User.class).withUseHeader(true);
        String csv;
        try {
            csv = csvMapper.writer(schema).writeValueAsString(userDtos);
            return ResponseEntity.ok(csv);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
