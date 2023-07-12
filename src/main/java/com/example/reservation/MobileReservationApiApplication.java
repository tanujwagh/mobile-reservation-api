package com.example.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MobileReservationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileReservationApiApplication.class, args);
    }

}
