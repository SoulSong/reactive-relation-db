package com.shf.spring.h2;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.test.StepVerifier;

import static org.springframework.data.r2dbc.query.Criteria.where;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/7/22 15:58
 */
@Log4j2
public class H2App {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

        DatabaseClient client = DatabaseClient.create(connectionFactory);

        client.execute()
                .sql("CREATE TABLE person" +
                        "(id VARCHAR(255) PRIMARY KEY," +
                        "name VARCHAR(255)," +
                        "age INT)")
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        insert(client);

        select(client);

        update(client);

        select(client);

        update2(client);

        select(client);

        delete(client);

    }

    private static void insert(DatabaseClient client) {
        client.insert()
                .into(Person.class)
                .using(new Person("joe", "Joe", 34))
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    private static void select(DatabaseClient client) {
        client.select()
                .from(Person.class)
                .fetch()
                .all()
                .doOnNext(log::info)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    private static void update(DatabaseClient client) {
        client.execute()
                .sql("UPDATE person SET age = 35 where age=34")
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }

    private static void update2(DatabaseClient client) {
        client.update().table("person")
                .using(Update.update("age", 36))
                .matching(where("age").is(35))
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }

    private static void delete(DatabaseClient client) {
        client.delete()
                .from(Person.class)
                .matching(where("age").is(36)
                        .and("name").is("Joe"))
                .fetch()
                .rowsUpdated()
                .doOnNext(count -> log.info("delete {} item", count))
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Person {

        private String id;
        private String name;
        private int age;

    }
}
