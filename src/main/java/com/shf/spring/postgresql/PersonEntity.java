package com.shf.spring.postgresql;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

import javax.annotation.Generated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/7/22 15:13
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PersonEntity implements Serializable {

    @Id
    private Long id;
    private String name;
    private int age;

}
