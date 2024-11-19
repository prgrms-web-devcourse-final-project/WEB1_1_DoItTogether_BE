package com.doittogether.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DoItTogetherApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoItTogetherApplication.class, args);
    }

}
