package com.epam.university.java.project.core.cdi.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "list")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListDefinitionImpl implements ListDefinition {

    @XmlElement(name = "value", type = ListItemDefinitionImpl.class)
    private List<ListItemDefinition> items;

    @Override
    public Collection<ListItemDefinition> getItems() {
        return items;
    }

    @Override
    public void setItems(Collection<ListItemDefinition> items) {
        this.items = new ArrayList<>(items);
    }
}