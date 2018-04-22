package com.filip.klose.wophillcoinbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.ConfigurationType;
import com.filip.klose.wophillcoinbank.entity.Configuration;
import com.filip.klose.wophillcoinbank.repository.ConfigurationRepository;
import com.filip.klose.wophillcoinbank.runtime.exception.ConfigurationNotFoundException;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public Configuration getInterestConfiguration() throws ConfigurationNotFoundException {
        return configurationRepository.findConfigurationByType(ConfigurationType.INTEREST.name())
                .orElseThrow(ConfigurationNotFoundException::new);
    }

    public Configuration putInterestConfiguration(int interest) {
        Configuration interestConfiguration;
        try {
            interestConfiguration = getInterestConfiguration();
        } catch (ConfigurationNotFoundException e) {
            interestConfiguration = new Configuration(ConfigurationType.INTEREST.name());
        }
        interestConfiguration.setValue(String.valueOf(interest));
        return configurationRepository.save(interestConfiguration);
    }
}
