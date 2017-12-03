package com.reconnect.web.utils;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = {
        "com.reconnect.web.utils.example.models",
        "com.reconnect.web.utils.auth.model"
})
public class TestConfig {
}
