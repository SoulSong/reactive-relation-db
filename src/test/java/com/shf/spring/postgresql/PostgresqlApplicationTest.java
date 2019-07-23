package com.shf.spring.postgresql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.log4j.Log4j2;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PostgresqlApplication.class})
@Log4j2
public class PostgresqlApplicationTest {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    private DatabaseClient client;

    @Test
    public void insert() {
        client.insert()
                .into("person_entity")
                .value("id", 1)
                .value("name", "bar")
                .value("age", 11).fetch().rowsUpdated()
                .then(client.execute().sql("INSERT INTO person_entity (id, name, age) VALUES(:id, :name, :age)")
                        .bind("id", 2)
                        .bind("name", "xxx")
                        .bind("age", 11)
                        .fetch().rowsUpdated())
                .then().as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    public void update() {
        personRepository.save(PersonEntity.builder().age(12).name("foo").id(1L).build())
                .as(StepVerifier::create).expectNextCount(1).verifyComplete();
    }

    @Test
    public void findAll() {
        personRepository.findAll()
                .doOnNext(personEntity -> log.info(personEntity.toString()))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void findByAge() {
        personRepository.findByAge(12)
                .doOnNext(personEntity -> log.info(personEntity.toString()))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}