package com.reconnect.web.utils.example.config;

import com.reconnect.web.utils.mapper.MapperPackages;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
@Configuration
@AllArgsConstructor
public class BeanConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Bean for MapperPackages setup.
     *
     * @return bean
     */
    @Bean
    public MapperPackages mapperPackages() {
        return new MapperPackages("com.reconnect.web.utils.services.mappers");
    }
}