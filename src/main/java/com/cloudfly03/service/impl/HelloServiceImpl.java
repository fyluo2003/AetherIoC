package com.cloudfly03.service.impl;

import com.cloudfly03.dao.HelloDao;
import com.cloudfly03.factory.BeanFactory;
import com.cloudfly03.service.HelloService;

import java.util.List;

public class HelloServiceImpl implements HelloService {
    private final HelloDao helloDao = (HelloDao) BeanFactory.getDao("helloDao");

    @Override
    public List<String> findAll() {
        return helloDao.findAll();
    }
}
