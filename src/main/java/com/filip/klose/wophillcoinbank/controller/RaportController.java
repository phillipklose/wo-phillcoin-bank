package com.filip.klose.wophillcoinbank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.filip.klose.wophillcoinbank.entity.LoanCash;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.mapper.LoanCashMapper;
import com.filip.klose.wophillcoinbank.mapper.UserMapper;
import com.filip.klose.wophillcoinbank.model.LoanCashDto;
import com.filip.klose.wophillcoinbank.model.RaportDto;
import com.filip.klose.wophillcoinbank.model.UserDto;
import com.filip.klose.wophillcoinbank.service.LoanCashService;
import com.filip.klose.wophillcoinbank.service.UserService;

@RestController
@RequestMapping(value = "/raport", produces = "text/csv")
public class RaportController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoanCashService loanCashService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoanCashMapper loanCashMapper;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getReportForAllUsers() {
        List<RaportDto> raports = new ArrayList<>();
        final List<User> users = userService.getAllUsers();
        for (User user : users) {
            final UserDto userDto = userMapper.convertToDto(user);
            final List<LoanCash> laonByUserId = loanCashService.getByUserId(userDto.getId());
            if (laonByUserId.size() > 0) {
                LoanCashDto[] loans = new LoanCashDto[1000];
                int iterator = 0;
                for (LoanCash lc : laonByUserId) {
                    final LoanCashDto loanCashDto = loanCashMapper.convertToDto(lc);
                    loans[iterator++] = loanCashDto;
                }
                RaportDto raportDto = new RaportDto(userDto);
//                raportDto.setLoans(loans);
                raports.add(raportDto);
            } else {
                raports.add(new RaportDto(userDto));
            }

        }
        final CsvMapper csvMapper = new CsvMapper();
        final CsvSchema schema = csvMapper.schemaFor(RaportDto.class).withUseHeader(true);
        String csv;
        try {
            csv = csvMapper.writer(schema).writeValueAsString(raports);
            return ResponseEntity.ok(csv);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
