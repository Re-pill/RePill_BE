package com.repill.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RePillApplication {

    public static void main(String[] args) {
        SpringApplication.run(RePillApplication.class, args);
    }

}
