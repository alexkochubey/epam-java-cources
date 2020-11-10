package com.epam.university.java.project.core.cdi.structure;

import com.epam.university.java.project.core.cdi.structure.ListDefinition.ListItemDefinition;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListItemDefinitionImpl implements ListItemDefinition {

    @XmlValue
    private String value;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}