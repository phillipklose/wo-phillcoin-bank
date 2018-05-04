package com.filip.klose.wophillcoinbank.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.SaldoDto;
import com.filip.klose.wophillcoinbank.model.UserDto;

@Service
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public SaldoDto convertToSaldoDto(User user) {
        SaldoDto saldoDto = modelMapper.map(user, SaldoDto.class);
        return saldoDto;
    }

}
