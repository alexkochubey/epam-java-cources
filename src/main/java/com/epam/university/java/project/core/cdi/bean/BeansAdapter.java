package com.epam.university.java.project.core.cdi.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "beans")
@XmlAccessorType(XmlAccessType.FIELD)
public class BeansAdapter {
    @XmlElement(name = "bean", type = BeanDefinitionImpl.class)
    private List<BeanDefinition> list;

    public List<BeanDefinition> getList() {
        return list;
    }

    public void setList(List<BeanDefinition> list) {
        this.list = list;
    }
}