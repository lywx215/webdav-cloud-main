package com.github.zxbu.webdavteambition.config;

import com.github.zxbu.webdavteambition.client.BaiduDriverClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BaiduDriverProperties.class)
public class BaiduDriverAutoConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaiduDriverAutoConfig.class);

    @Autowired
    private BaiduDriverProperties baiduDriverProperties;

    @Bean
    public BaiduDriverClient baiduClient(ApplicationContext applicationContext) throws Exception {
        return new BaiduDriverClient(baiduDriverProperties);
    }



}
