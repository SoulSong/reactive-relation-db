package com.shf.spring.postgresql;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;

import reactor.core.publisher.Flux;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/7/23 21:50
 */
public interface PersonRepository extends R2dbcRepository<PersonEntity, Long> {

    @Query("SELECT * FROM person_entity WHERE age = :age")
    Flux<PersonEntity> findByAge(int age);

}
