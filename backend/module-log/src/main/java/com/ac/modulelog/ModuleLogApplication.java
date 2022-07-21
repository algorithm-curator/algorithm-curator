package com.ac.modulelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EntityScan(basePackages = {"com.ac.modulecommon"})
@EnableJpaRepositories(basePackages = {"com.ac.modulecommon"})
@ConfigurationPropertiesScan(basePackages = {"com.ac.modulecommon", "com.ac.modulelog"})
@SpringBootApplication(scanBasePackages = {"com.ac.modulecommon", "com.ac.modulelog"})
public class ModuleLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleLogApplication.class, args);
    }

}
