package com.cloudfly03.spring.entity;

import lombok.Data;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;

@Data
//@Component("myOrder")
public class Order {
//    @Value("xxx123")
    private String orderId;
//    @Value("100.0")
    private Float price;
}
