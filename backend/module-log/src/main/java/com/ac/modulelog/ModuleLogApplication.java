package com.ac.modulelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.ac.modulecommon", "com.ac.modulelog"})
public class ModuleLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleLogApplication.class, args);
    }

}
