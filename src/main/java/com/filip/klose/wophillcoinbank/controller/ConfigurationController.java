package com.filip.klose.wophillcoinbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.filip.klose.wophillcoinbank.entity.Configuration;
import com.filip.klose.wophillcoinbank.mapper.ConfigurationMapper;
import com.filip.klose.wophillcoinbank.service.ConfigurationService;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ConfigurationMapper mapper;

    @PutMapping("/interest")
    @ResponseBody
    public ResponseEntity<?> setGlobalInterestValue(@RequestParam("value") int value) {
        final Configuration configuration = configurationService.putInterestConfiguration(value);
        return ResponseEntity.ok(mapper.convertToDto(configuration));
    }
}
