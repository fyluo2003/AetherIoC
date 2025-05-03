package com.cloudfly03.myspring;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyAnnotationConfigApplicationContext {
    private Map<String, Object> ioc = new HashMap<>();

    public MyAnnotationConfigApplicationContext(String pack) {
        //遍历包，找到目标类(原材料)
        Set<BeanDefinition> beanDefinitions = findBeanDefinitions(pack);
        //根据原材料创建bean
        createObject(beanDefinitions);
        //自动装载
        autowireObject(beanDefinitions);
    }

    public Object getBean(String beanName) {
        return ioc.get(beanName);
    }

    public void createObject(Set<BeanDefinition> beanDefinitions) {
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            Class<?> clazz = beanDefinition.getBeanClass();
            String beanName = beanDefinition.getBeanName();
            try{
                //创建对象
                Object object = clazz.getConstructor().newInstance();
                //属性赋值
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    Value valueAnnotation = declaredField.getAnnotation(Value.class);
                    if (valueAnnotation != null) {
                        String value = valueAnnotation.value();
                        String fileName = declaredField.getName();
                        String methodName = "set" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
                        Method method = clazz.getMethod(methodName, declaredField.getType());
                        //完成value数据类型的转换
                        Object val = null;
                        switch (declaredField.getType().getName()){
                            case "java.lang.Integer":
                                val = Integer.parseInt(value);
                                break;
                            case "java.lang.String":
                                val = value;
                                break;
                            case "java.lang.Float":
                                val = Float.parseFloat(value);
                                break;
                        }
                        method.invoke(object, val);
                    }
                }
                ioc.put(beanName, object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void autowireObject(Set<BeanDefinition> beanDefinitions) {
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            Class<?> clazz = beanDefinition.getBeanClass();
            for (Field declaredField : clazz.getDeclaredFields()) {
                Autowired autowiredAnnotation = declaredField.getAnnotation(Autowired.class);
                if (autowiredAnnotation != null) {
                    Qualifier qualifierAnnotation = declaredField.getAnnotation(Qualifier.class);
                    if (qualifierAnnotation != null) {
                        //byName
                        String beanName = qualifierAnnotation.value();
                        Object bean = getBean(beanName);
                        String fieldName = declaredField.getName();
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        try {
                            Method method = clazz.getMethod(methodName, declaredField.getType());
                            Object object = getBean(beanDefinition.getBeanName());
                            method.invoke(object, bean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        //byType
                        for (String beanName : ioc.keySet()) {
                            if (ioc.get(beanName).getClass() == declaredField.getType()) {
                                String fieldName = declaredField.getName();
                                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                                try {
                                    Method method = clazz.getMethod(methodName, declaredField.getType());
                                    Object object = getBean(beanDefinition.getBeanName());
                                    Object bean = getBean(beanName);
                                    method.invoke(object, bean);
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Set<BeanDefinition> findBeanDefinitions(String pack) {
        //1.获取包下的所有类
        Set<Class<?>> classes = MyTools.getClasses(pack);
        Iterator<Class<?>> iterator = classes.iterator();
        Set<BeanDefinition> beanDefinitions = new HashSet<BeanDefinition>();
        while (iterator.hasNext()) {
            //2.遍历类，找到添加注解的类
            Class<?> clazz = iterator.next();
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            if (componentAnnotation != null) {
                //获取component注解中的name属性
                String beanName = componentAnnotation.value();
                if (beanName.equals("")) {
                    //获取类名小写
                    String className = clazz.getName().replaceAll(clazz.getPackage().getName() + ".", "");
                    beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
                }
                //3.将类名和类对象封装到BeanDefinition，装载到集合中
                beanDefinitions.add(new BeanDefinition(beanName, clazz));
            }
        }

        return beanDefinitions;
    }
}
