package com.cloudfly03.dao.impl;

import com.cloudfly03.dao.HelloDao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HelloDaoImpl implements HelloDao {
    @Override
    public List<String> findAll() {
        return Arrays.asList("1", "2", "3");
    }
}
