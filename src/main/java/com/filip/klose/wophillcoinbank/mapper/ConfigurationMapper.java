package com.filip.klose.wophillcoinbank.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.Configuration;
import com.filip.klose.wophillcoinbank.model.ConfigurationDto;

@Service
public class ConfigurationMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ConfigurationDto convertToDto(Configuration configuration) {
        ConfigurationDto configurationDto = modelMapper.map(configuration, ConfigurationDto.class);
        return configurationDto;
    }

}
