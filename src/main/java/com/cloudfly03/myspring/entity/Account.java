package com.cloudfly03.myspring.entity;

import com.cloudfly03.myspring.Autowired;
import com.cloudfly03.myspring.Component;
import com.cloudfly03.myspring.Qualifier;
import com.cloudfly03.myspring.Value;
import lombok.Data;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Repository;

@Data
@Component("account")
public class Account {
    @Value("1")
    private Integer id;
    @Value("cloudfly03")
    private String name;
    @Value("21")
    private Integer age;

    @Autowired
    @Qualifier("myOrder")
    private Order order;
}
