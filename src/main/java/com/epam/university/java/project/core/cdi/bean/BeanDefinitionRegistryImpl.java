package com.epam.university.java.project.core.cdi.bean;

import java.util.HashMap;

public class BeanDefinitionRegistryImpl implements BeanDefinitionRegistry {

    private HashMap<String, BeanDefinition> beans = new HashMap<>();

    @Override
    public void addBeanDefinition(BeanDefinition definition) {
        String name = definition.getId();
        beans.put(name,definition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        BeanDefinition beanDefinition = beans.get(beanId);
        return beanDefinition;
    }
}