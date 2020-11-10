package com.epam.university.java.project.core.cdi.bean;

import com.epam.university.java.project.core.cdi.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.Collection;

public class BeanDefinitionReaderImpl implements BeanDefinitionReader {
    private BeanDefinitionRegistry registry;

    public BeanDefinitionReaderImpl(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public int loadBeanDefinitions(Resource resource) {
        Collection<BeanDefinition> beanDefinitions = null;
        try {
            JAXBContext context1 = JAXBContext.newInstance(BeansAdapter.class);
            Unmarshaller unmarshaller = context1.createUnmarshaller();
            BeansAdapter beansAdapter = (BeansAdapter) unmarshaller.unmarshal(resource.getFile());
            if (!isCorrectBeansAdapter(beansAdapter)) {
                throw new RuntimeException("BeansAdapter is unCorrect, XML is Correct?");
            }
            beanDefinitions = beansAdapter.getList();
        } catch (Exception e) {
            System.out.println("JAXB can't read file : " + resource.getFile().getName());
        }
        if (beanDefinitions == null || beanDefinitions.isEmpty()) {
            throw new NullPointerException("JAXB didn't create correct list");
        }

        int amountLoadedBeans = 0;
        for (BeanDefinition beanDefinition : beanDefinitions) {
            amountLoadedBeans++;
            registry.addBeanDefinition(beanDefinition);
        }

        return amountLoadedBeans;
    }

    @Override
    public int loadBeanDefinitions(Collection<Resource> resources) {
        int countLoaded = 0;
        for (Resource resource : resources) {
            countLoaded += loadBeanDefinitions((Resource) resource.getFile());
        }
        return countLoaded;
    }


    private boolean isCorrectBeansAdapter(BeansAdapter adapter) {
        Collection<BeanDefinition> beanDefinitions = adapter.getList();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Collection<BeanPropertyDefinition> properties = beanDefinition.getProperties();
            if (properties == null) {
                continue;
            }
            for (BeanPropertyDefinition propertyDefinition : properties) {
                if (propertyDefinition.getName() == null) {
                    return false;
                }
                if (propertyDefinition.getRef() == null
                        && propertyDefinition.getData() == null
                        && propertyDefinition.getValue() == null) {
                    return false;
                }
            }
        }
        return true;
    }
}