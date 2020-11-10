package com.epam.university.java.project.core.cdi.structure;

import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "map")
@XmlAccessorType(XmlAccessType.FIELD)
public class MapDefinitionImpl implements MapDefinition {

    @XmlElements({@XmlElement(type = MapEntryDefinitionImpl.class, name = "entry")})
    private Collection<MapEntryDefinition> values;

    @Override
    public Collection<MapEntryDefinition> getValues() {
        return values;
    }

    @Override
    public void setValues(Collection<MapEntryDefinition> values) {
        this.values = values;
    }
}