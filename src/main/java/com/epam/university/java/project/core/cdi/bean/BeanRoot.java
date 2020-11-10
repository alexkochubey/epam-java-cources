package com.epam.university.java.project.core.cdi.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "beans")
@XmlAccessorType(XmlAccessType.FIELD)
public class BeanRoot {
    @XmlElement(name = "bean", type = BeanDefinitionImpl.class)
    private List<BeanDefinition> beansList = new ArrayList<>();

    public List<BeanDefinition> getBeansList() {
        return beansList;
    }

    public void setLisOfBeans(List<BeanDefinition> beansList) {
        this.beansList = beansList;
    }

}