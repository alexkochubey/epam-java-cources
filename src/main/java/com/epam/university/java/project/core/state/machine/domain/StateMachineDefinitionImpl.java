package com.epam.university.java.project.core.state.machine.domain;

import com.epam.university.java.project.domain.BookEvent;
import com.epam.university.java.project.domain.BookStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "definition")
public class StateMachineDefinitionImpl implements StateMachineDefinition {
    @XmlAttribute
    private BookEvent startEvent;
    @XmlAttribute
    private BookStatus startState;
    @XmlAttribute
    private Class<? extends StateMachineEventHandler> handler;
    @XmlElement(name = "transition", type = StateMachineStateImpl.class)
    private Collection<StateMachineState<BookStatus, BookEvent>> states = new ArrayList<>();

    @Override
    public Object getStartEvent() {
        return startEvent;
    }

    @Override
    public Object getStartState() {
        return startState;
    }

    @Override
    public void setStartEvent(Object o) {
        startEvent = (BookEvent) o;
    }

    @Override
    public void setStartState(Object o) {
        startState = (BookStatus) o;
    }

    @Override
    public Collection<StateMachineState> getStates() {
        return new ArrayList<>(states);
    }

    @Override
    public void addState(StateMachineState state) {
        states.add(state);
    }

    @Override
    public Class<? extends StateMachineEventHandler> getHandlerClass() {
        return handler;
    }

    @Override
    public void setHandlerClass(Class handlerClass) {
        this.handler = handlerClass;
    }
}