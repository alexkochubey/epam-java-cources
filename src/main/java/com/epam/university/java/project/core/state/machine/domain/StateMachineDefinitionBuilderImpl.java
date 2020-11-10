package com.epam.university.java.project.core.state.machine.domain;

import com.epam.university.java.project.domain.BookEvent;
import com.epam.university.java.project.domain.BookStatus;

public class StateMachineDefinitionBuilderImpl implements StateMachineDefinitionBuilder {

    private StateMachineDefinition<BookStatus, BookEvent> definition;

    @Override
    public StateMachineDefinition build() {
        definition = new StateMachineDefinitionImpl();
        return definition;
    }

    @Override
    public StateMachineDefinitionBuilder addState(Object from, Object to, Object on,
                                                  String method) {
        StateMachineState<BookStatus, BookEvent> state = new StateMachineStateImpl();
        state.setFrom((BookStatus) from);
        state.setTo((BookStatus) to);
        state.setOn((BookEvent) on);
        state.setMethodToCall(method);
        definition.addState(state);
        return this;
    }

    @Override
    public StateMachineDefinitionBuilder addState(Object from, Object to, Object on) {
        StateMachineState<BookStatus, BookEvent> state = new StateMachineStateImpl();
        state.setFrom((BookStatus) from);
        state.setTo((BookStatus) to);
        state.setOn((BookEvent) on);
        definition.addState(state);
        return this;
    }
}