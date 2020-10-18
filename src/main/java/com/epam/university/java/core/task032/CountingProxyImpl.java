package com.epam.university.java.core.task032;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CountingProxyImpl implements CountingProxy {

    private int invocationCountAction = 0;
    private int invocationCountAnotherAction = 0;
    private Object someActionExecutor;

    @Override
    public int getInvocationsCount(String methodName) {
        if (methodName.equals("doAction")) {
            return invocationCountAction;
        } else if (methodName.equals("doAnotherAction")) {
            return invocationCountAnotherAction;
        }
        return 0;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("doAction")) {
            this.invocationCountAction++;
            return method.invoke(someActionExecutor, args);
        } else if (method.getName().equals("doAnotherAction")) {
            this.invocationCountAnotherAction++;
            return method.invoke(someActionExecutor, args);
        }
        return null;
    }

    public SomeActionExecutor setExecutor(Object someActionExecutor) {
        this.someActionExecutor = someActionExecutor;
        return (SomeActionExecutor) Proxy
                .newProxyInstance(someActionExecutor.getClass().getClassLoader(),
                        someActionExecutor.getClass().getInterfaces(), this);
    }
}