package com.nivishay.nmp;

import com.nivishay.nmp.users.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class NetworkMonitoringPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkMonitoringPlatformApplication.class, args);
    }


    @Bean
    CommandLineRunner test(UserService userService) {
        return args -> {
            UUID id = userService.createUser("Test@controller2.com", "password");
            System.out.println("Created user: " + id);
        };
    }
}


