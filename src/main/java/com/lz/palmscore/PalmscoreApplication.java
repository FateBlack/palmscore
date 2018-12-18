package com.lz.palmscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class PalmscoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(PalmscoreApplication.class, args);
    }
}
