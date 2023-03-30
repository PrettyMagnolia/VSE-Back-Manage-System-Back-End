package com.backend.vse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.backend.vse.mapper")
public class VSEApplication {

    public static void main(String[] args) {
        SpringApplication.run(VSEApplication.class, args);
    }

}
