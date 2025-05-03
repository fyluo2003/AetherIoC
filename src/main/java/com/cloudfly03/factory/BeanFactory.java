package com.cloudfly03.factory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    private static final Properties properties;

    private static final Map<String ,Object> cache = new ConcurrentHashMap<>();

    static {
        properties = new Properties();
        try {
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("factory.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getDao(String beanName) {
        // 没有对象，创建对象并放入缓存
        if (!cache.containsKey(beanName)) {
            synchronized (BeanFactory.class) {
                if (!cache.containsKey(beanName)) {
                    try {
                        String value = properties.getProperty(beanName);
                        // 反射创建对象
                        Class<?> clazz = Class.forName(value);
                        // 调用无参构造器创建对象
                        Object object = clazz.getConstructor().newInstance();
                        cache.put(beanName, object);
                    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        // 有对象，直接返回
        return cache.get(beanName);
    }
}
