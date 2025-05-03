package com.cloudfly03.dao.impl;

import com.cloudfly03.dao.HelloDao;

import java.util.Arrays;
import java.util.List;

public class HelloDaoImpl2 implements HelloDao {
    @Override
    public List<String> findAll() {
        return Arrays.asList("4", "5", "6");
    }
}
