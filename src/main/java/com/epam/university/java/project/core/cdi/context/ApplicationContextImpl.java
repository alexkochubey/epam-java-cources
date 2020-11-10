package com.epam.university.java.project.core.cdi.context;

import com.epam.university.java.project.core.cdi.io.Resource;

import java.util.Collection;

public class ApplicationContextImpl implements ApplicationContext {
    @Override
    public int loadBeanDefinitions(Resource resource) {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(Collection<Resource> resources) {
        return 0;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return null;
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        return null;
    }
}
