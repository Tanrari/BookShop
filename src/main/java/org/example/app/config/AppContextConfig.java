package org.example.app.config;

import org.example.app.service.IdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppContextConfig {
//1
    @Bean
    @Scope(value = "prototype")
    public IdProvider provider(){
        return new IdProvider();
    }

//    @Bean
//    ConversionServiceFactoryBean conversionService() {
//        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
//        Set<Converter> converters = new HashSet<>();
//        converters.add(new UploadConverter());
//        bean.setConverters(converters);
//        return bean;
//    }

}
