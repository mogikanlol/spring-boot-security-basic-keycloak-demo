package com.example.client;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@EnableFeignClients
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(HelloClient client) {
        return args -> {
            String header = "Basic " + Base64.getEncoder().encodeToString("demo:demo".getBytes(StandardCharsets.UTF_8));
            System.out.println(client.getHello(header));
        };
    }
}
