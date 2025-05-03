package com.cloudfly03.spring.test;

//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cloudfly03.myspring.MyAnnotationConfigApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        //加载ioc容器
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.cloudfly03.spring.entity");
//        System.out.println(applicationContext.getBean("account"));

        MyAnnotationConfigApplicationContext myAnnotationConfigApplicationContext = new MyAnnotationConfigApplicationContext("com.cloudfly03.myspring.entity");
        System.out.println(myAnnotationConfigApplicationContext.getBean("account"));
    }
}
