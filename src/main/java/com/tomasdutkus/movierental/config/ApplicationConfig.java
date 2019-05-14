package com.tomasdutkus.movierental.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.tomasdutkus.movierental")
@EntityScan("com.tomasdutkus.movierental.model")
@EnableJpaRepositories("com.tomasdutkus.movierental.dao")
public class ApplicationConfig {
}
