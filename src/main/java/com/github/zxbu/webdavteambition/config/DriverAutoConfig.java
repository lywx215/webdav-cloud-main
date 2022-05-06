package com.github.zxbu.webdavteambition.config;

import com.github.zxbu.webdavteambition.client.DriverClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DriveProperties.class)
public class DriverAutoConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverAutoConfig.class);

    @Autowired
    private DriveProperties  driveProperties;

    @Bean
    public DriverClient teambitionClient(ApplicationContext applicationContext) throws Exception {
        return new DriverClient(driveProperties);
    }



}
