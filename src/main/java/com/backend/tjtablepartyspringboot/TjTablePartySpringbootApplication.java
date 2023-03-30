package com.backend.tjtablepartyspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.backend.tjtablepartyspringboot.mapper")
public class TjTablePartySpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TjTablePartySpringbootApplication.class, args);
    }

}
