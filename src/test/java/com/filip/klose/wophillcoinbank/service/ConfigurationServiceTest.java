package com.filip.klose.wophillcoinbank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.filip.klose.wophillcoinbank.ConfigurationType;
import com.filip.klose.wophillcoinbank.entity.Configuration;
import com.filip.klose.wophillcoinbank.repository.ConfigurationRepository;
import com.filip.klose.wophillcoinbank.runtime.exception.ConfigurationNotFoundException;

@RunWith(SpringRunner.class)
public class ConfigurationServiceTest {

    @TestConfiguration
    static class ConfigurationServiceTestContextConfiguration {

        @Bean
        public ConfigurationService configurationService() {
            return new ConfigurationService();
        }
    }

    @Autowired
    private ConfigurationService configurationService;

    @MockBean
    private ConfigurationRepository configurationRepository;

    private Configuration configuration;
    private int interest = 10;

    @Before
    public void setup() {
        configuration = new Configuration(ConfigurationType.INTEREST.name());
        configuration.setValue(String.valueOf(interest));
        configuration.setId(new ObjectId());
    }

    @Test
    public void testGetInterestConfigurationThatExistsAndShouldReturnIt() throws Exception {
        when(configurationRepository.findConfigurationByType(ConfigurationType.INTEREST.name())).thenReturn(Optional.of(configuration));

        final Configuration interestConfiguration = configurationService.getInterestConfiguration();

        assertNotNull(interestConfiguration.getId());
        assertEquals(interestConfiguration.getType(), ConfigurationType.INTEREST.name());
        assertEquals(interestConfiguration.getValue(), String.valueOf(interest));
    }

    @Test(expected = ConfigurationNotFoundException.class)
    public void testGetInterestConfigurationThatDoesNotExistsAndThrowsConfigurationNotFoundException()
            throws ConfigurationNotFoundException {
        final Configuration interestConfiguration = configurationService.getInterestConfiguration();
    }

    @Test
    public void testPutInterestConfigurationWithNoExistingOne() {
        when(configurationRepository.save(any())).thenReturn(configuration);

        final Configuration interestConfiguration = configurationService.putInterestConfiguration(interest);

        assertNotNull(interestConfiguration.getId());
        assertEquals(interestConfiguration.getType(), ConfigurationType.INTEREST.name());
        assertEquals(interestConfiguration.getValue(), String.valueOf(interest));
    }

    @Test
    public void testPutInterestConfigurationWithExistingOne() {
        when(configurationRepository.findConfigurationByType(ConfigurationType.INTEREST.name())).thenReturn(Optional.of(configuration));
        when(configurationRepository.save(any())).thenReturn(configuration);

        final Configuration interestConfiguration = configurationService.putInterestConfiguration(interest);

        assertNotNull(interestConfiguration.getId());
        assertEquals(interestConfiguration.getType(), ConfigurationType.INTEREST.name());
        assertEquals(interestConfiguration.getValue(), String.valueOf(interest));
    }
}