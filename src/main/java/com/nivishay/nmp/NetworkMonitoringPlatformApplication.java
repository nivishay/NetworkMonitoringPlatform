package com.nivishay.nmp;

import com.nivishay.nmp.auth.infra.JwtProperties;
import com.nivishay.nmp.users.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@ConfigurationPropertiesScan
@SpringBootApplication
public class NetworkMonitoringPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkMonitoringPlatformApplication.class, args);
    }

    @Profile("dev")
    @Bean
    CommandLineRunner test(UserService userService) {
        return args -> {
            UUID id = userService.createUser("Test@controller2.com", "password");
            System.out.println("Created user: " + id);
        };
    }
    @Bean
    CommandLineRunner testJWTProps(UserService userService, JwtProperties jwtProps) {
        return args -> System.out.println(jwtProps.getIssuer());
    }
}


