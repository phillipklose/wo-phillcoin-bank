package com.filip.klose.wophillcoinbank.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.filip.klose.wophillcoinbank.builder.UserBuilder;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.SaldoDto;
import com.filip.klose.wophillcoinbank.model.UserDto;

public class UserMapperTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testIfConvertedEntityToDtoWorksProperly() {
        // given
        User userEntity = new UserBuilder().setLogin("testLogin").setPassword("password").setFirstName("testFirstName")
                .setLastName("testLastName").setEmail("testEmail").setSaldo(0).build();

        // when
        UserDto convertedUserEntityToDto = modelMapper.map(userEntity, UserDto.class);

        // then
        assertEquals(convertedUserEntityToDto.getLogin(), userEntity.getLogin());
        assertEquals(convertedUserEntityToDto.getFirstName(), userEntity.getFirstName());
        assertEquals(convertedUserEntityToDto.getLastName(), userEntity.getLastName());
        assertEquals(convertedUserEntityToDto.getEmail(), userEntity.getEmail());
        assertEquals(convertedUserEntityToDto.getSaldo(), userEntity.getSaldo());
    }

    @Test
    public void testIfConvertedEntityToSaldoDtoWorksProperly() {
        // given
        User userEntity = new UserBuilder().setLogin("testLogin").setPassword("password").setFirstName("testFirstName")
                .setLastName("testLastName").setEmail("testEmail").setSaldo(0).build();

        // when
        SaldoDto convertedUserEntityToSaldoDto = modelMapper.map(userEntity, SaldoDto.class);

        // then
        assertEquals(convertedUserEntityToSaldoDto.getSaldo(), userEntity.getSaldo());
    }
}