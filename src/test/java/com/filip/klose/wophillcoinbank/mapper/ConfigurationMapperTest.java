package com.filip.klose.wophillcoinbank.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.filip.klose.wophillcoinbank.ConfigurationType;
import com.filip.klose.wophillcoinbank.entity.Configuration;
import com.filip.klose.wophillcoinbank.model.ConfigurationDto;

public class ConfigurationMapperTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testIfInterestConfigurationIsMappedCorrectlyToDto() {
        Configuration configuration = new Configuration(ConfigurationType.INTEREST.name());
        configuration.setValue("777");
        configuration.setId(new ObjectId());

        ConfigurationDto configurationDto = modelMapper.map(configuration, ConfigurationDto.class);

        assertNotNull(configurationDto.getId());
        assertEquals(configurationDto.getType(), configuration.getType());
        assertEquals(configurationDto.getValue(), configuration.getValue());
    }

}