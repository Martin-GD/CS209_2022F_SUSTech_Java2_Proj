package com.springboottest.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.springboottest.demo.*")
@SpringBootApplication
public class  SpringboottestApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringboottestApplication.class, args);
    }

}
