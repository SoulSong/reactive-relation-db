package com.shf.spring.postgresql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author songhaifeng
 */
@SpringBootApplication
@EnableR2dbcRepositories
public class PostgresqlApplication  {

    public static void main(String[] args) {
        SpringApplication.run(PostgresqlApplication.class, args);
    }

}
