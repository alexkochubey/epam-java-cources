package com.epam.university.java.project.core.state.machine.domain;

public class StatefulEntityImpl implements StatefulEntity {

    private StateMachineDefinition definition;
    private Object state;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void setState(Object o) {
        this.state = o;
    }

    @Override
    public StateMachineDefinition getStateMachineDefinition() {
        return definition;
    }

    @Override
    public void setStateMachineDefinition(StateMachineDefinition definition) {
        this.definition = definition;
    }
}