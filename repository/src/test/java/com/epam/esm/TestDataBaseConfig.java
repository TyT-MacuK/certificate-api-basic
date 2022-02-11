package com.epam.esm;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan
@SpringBootConfiguration
@EnableAutoConfiguration
@EntityScan("com.epam.esm.entity")
@EnableJpaRepositories("com.epam.esm.dao")
public class TestDataBaseConfig {
}