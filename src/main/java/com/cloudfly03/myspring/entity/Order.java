package com.cloudfly03.myspring.entity;

import com.cloudfly03.myspring.Component;
import com.cloudfly03.myspring.Value;
import lombok.Data;

@Data
@Component("myOrder")
public class Order {
    @Value("xxx123")
    private String orderId;
    @Value("100.0")
    private Float price;
}
