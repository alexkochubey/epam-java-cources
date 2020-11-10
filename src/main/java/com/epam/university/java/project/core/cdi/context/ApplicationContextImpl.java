package com.epam.university.java.project.core.cdi.context;

import com.epam.university.java.project.core.cdi.bean.BeanDefinition;
import com.epam.university.java.project.core.cdi.bean.BeanDefinitionRegistryImpl;
import com.epam.university.java.project.core.cdi.bean.BeanPropertyDefinition;
import com.epam.university.java.project.core.cdi.bean.BeanRoot;
import com.epam.university.java.project.core.cdi.io.Resource;
import com.epam.university.java.project.core.cdi.structure.ListDefinitionImpl;
import com.epam.university.java.project.core.cdi.structure.MapDefinition;
import com.epam.university.java.project.core.cdi.structure.MapDefinitionImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ApplicationContextImpl implements ApplicationContext {

    BeanDefinitionRegistryImpl beanDefinitionRegistry = new BeanDefinitionRegistryImpl();
    private final Map<Class, Object> singletonCache = new ConcurrentHashMap<>();

    @Override
    public int loadBeanDefinitions(Resource resource) {
        JAXBContext jaxbContext;
        BeanRoot beanRoot;
        List<BeanDefinition> beansList;
        try {
            jaxbContext = JAXBContext.newInstance(BeanRoot.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            beanRoot = (BeanRoot) unmarshaller.unmarshal(resource.getFile());
            beansList = beanRoot.getBeansList();
            for (BeanDefinition beanDefinition : beansList) {
                beanDefinitionRegistry.addBeanDefinition(beanDefinition);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return beanDefinitionRegistry.getRegistrySize();
    }

    @Override
    public int loadBeanDefinitions(Collection<Resource> resources) {
        int numOfBeans = 0;
        for (Resource resource : resources) {
            numOfBeans += loadBeanDefinitions(resource);
        }
        return numOfBeans;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(Class<T> beanClass) {
        if (beanClass.isInterface()) {
            beanClass = (Class<T>) getImpl(beanClass);
        }
        String className = beanClass.getName()
                .toLowerCase()
                .replaceAll("\\w+\\.", "");
        return getBean(className, beanClass);
    }

    @Override
    public Object getBean(String beanName) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(
                    beanDefinitionRegistry.getBeanDefinition(beanName.toLowerCase())
                            .getClassName()
            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
        return getBean(beanName, clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        if (beanClass.isInterface()) {
            beanClass = (Class<T>) getImpl(beanClass);
        }
        String scope = null;
        if (beanDefinitionRegistry.getBeanDefinition(beanName.toLowerCase())
                .getScope() == null) {
            scope = "prototype";
        } else {
            scope = beanDefinitionRegistry.getBeanDefinition(beanName.toLowerCase())
                    .getScope();
        }
        T t = null;

        if (scope.equals("singleton") && singletonCache.containsKey(beanClass)) {
            return (T) singletonCache.get(beanClass);
        }

        try {
            t = beanClass.getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException();
        }
        if (beanDefinitionRegistry.getBeanDefinition(beanName.toLowerCase())
                .getProperties() == null) {
            singletonCache.put(beanClass, t);
            return t;
        }
        List<BeanPropertyDefinition> properties = new LinkedList<>(
                beanDefinitionRegistry.getBeanDefinition(beanName.toLowerCase())
                        .getProperties()
        );

        for (Field field : t.getClass()
                .getDeclaredFields()) {
            field.setAccessible(true);
            for (BeanPropertyDefinition prop : properties) {
                if (prop.getValue() == null && prop.getRef() == null && prop.getData() == null) {
                    throw new RuntimeException();
                }
                if (field.getName()
                        .equals(prop.getName())) {
                    try {
                        Class<?> type = field.getType();
                        String typeName = type.getName()
                                .toUpperCase()
                                .replaceAll("\\w+\\.", "");
                        if (typeName.equals("INT")) {
                            field.set(t, Integer.parseInt(prop.getValue()));
                        } else if (typeName.equals("STRING")) {
                            field.set(t, prop.getValue());
                        } else if (prop.getData() instanceof ListDefinitionImpl
                                && typeName.equals("COLLECTION")) {
                            field.set(t, ((ListDefinitionImpl) prop.getData()).getItems());
                        } else if (prop.getData() instanceof MapDefinitionImpl
                                && typeName.equals("MAP")) {
                            MapDefinition mapDefinition = (MapDefinition) prop.getData();
                            Map<String, Object> itemMap = new HashMap<>();
                            for (MapDefinition.MapEntryDefinition entryDefinition
                                    : mapDefinition.getValues()) {
                                if (entryDefinition.getValue() == null
                                        && entryDefinition.getRef() == null) {
                                    throw new RuntimeException();
                                }
                                if (field.getName()
                                        .equals("stringMap")
                                        && entryDefinition.getRef() != null) {
                                    throw new RuntimeException();
                                }
                                if (entryDefinition.getValue() != null) {
                                    itemMap.put(entryDefinition.getKey(),
                                            entryDefinition.getValue());
                                } else if (entryDefinition.getRef() != null) {
                                    Object dependency = getBean(entryDefinition.getRef());
                                    itemMap.put(entryDefinition.getKey(), dependency);
                                }
                            }
                            field.set(t, itemMap);
                        } else {
                            Object dependency = getBean(prop.getRef()
                                    .toLowerCase());
                            field.set(t, dependency);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException();
                    }
                }
            }
        }
        if (scope.equals("singleton") && !singletonCache.containsKey(beanClass)) {
            singletonCache.put(beanClass, t);
        }

        return t;
    }


    private <T> T getImpl(Class<T> beanClass) {
        List<BeanDefinition> list = beanDefinitionRegistry.getAllBeanDefinitions();
        Class<?> clazz = null;
        String beanName = beanClass.getName().toLowerCase();
        if (beanName.contains("interface")) {
            int pos = beanName.lastIndexOf('i');
            String name = beanClass.getName().substring(0, pos);
            try {
                clazz = Class.forName(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (BeanDefinition bean : list) {
            if (bean.getId().toLowerCase().equals(beanClass.getSimpleName().toLowerCase())) {
                try {
                    clazz = Class.forName(bean.getClassName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException();
                }
                if (clazz.getInterfaces().length != 0) {
                    String interfaces = clazz.getInterfaces()[0].getSimpleName();
                    if (beanClass.getSimpleName()
                            .equals(interfaces)) {
                        bean.setId(clazz.getName()
                                .toLowerCase()
                                .replaceAll("\\w+\\.", ""));
                        beanDefinitionRegistry.addBeanDefinition(bean);
                        break;
                    }
                }
            }
        }
        return (T) clazz;
    }
}