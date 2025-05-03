package com.cloudfly03.spring.entity;

import lombok.Data;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Repository;

@Data
//@Repository
public class Account {
//    @Value("1")
    private Integer id;
//    @Value("cloudfly03")
    private String name;
//    @Value("21")
    private Integer age;

//    @Autowired
//    @Qualifier("myOrder")
    private Order order;
}
