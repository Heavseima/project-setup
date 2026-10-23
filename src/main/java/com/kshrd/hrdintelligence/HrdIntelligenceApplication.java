package com.kshrd.hrdintelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HrdIntelligenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrdIntelligenceApplication.class, args);
    }
}
