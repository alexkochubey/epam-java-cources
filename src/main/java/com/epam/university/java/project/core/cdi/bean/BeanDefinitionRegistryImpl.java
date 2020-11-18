package com.epam.university.java.project.core.cdi.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanDefinitionRegistryImpl implements BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanCache = new ConcurrentHashMap<>();

    public int getRegistrySize() {
        return beanCache.size();
    }

    @Override
    public void addBeanDefinition(BeanDefinition definition) {
        beanCache.put(definition.getId().toLowerCase(), definition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return beanCache.get(beanId);
    }

    public List<BeanDefinition> getAllBeanDefinitions() {
        return new ArrayList<>(beanCache.values());
    }
}