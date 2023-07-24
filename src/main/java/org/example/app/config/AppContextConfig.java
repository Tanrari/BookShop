package org.example.app.config;

import org.example.app.service.IdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppContextConfig {

    @Bean
    @Scope(value = "prototype")
    public IdProvider provider(){
        return new IdProvider();
    }
}
