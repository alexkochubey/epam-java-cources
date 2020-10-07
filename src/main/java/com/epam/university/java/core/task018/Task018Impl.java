package com.epam.university.java.core.task018;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Task018Impl implements Task018 {
    @Override
    public boolean isAnnotationPresent(Object toCheck, Class<?> annotationToFind) {
        if (toCheck == null || annotationToFind == null) {
            throw new IllegalArgumentException();
        }
        Class<? extends Annotation> annotation = (Class<? extends Annotation>) annotationToFind;
        Class<?> objectClass = toCheck.getClass();
        for (Method m : objectClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(annotation)) {
                return true;
            }
            for (Parameter p : m.getParameters()) {
                if (p.isAnnotationPresent(annotation)) {
                    return true;
                }
            }
        }
        if (objectClass.isAnnotationPresent(annotation)) {
            return true;
        }
        for (Field f : objectClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(annotation)) {
                return true;
            }
        }
        for (Constructor c : objectClass.getDeclaredConstructors()) {
            if (c.isAnnotationPresent(annotation)) {
                return true;
            }
        }
        if (objectClass.getPackage().isAnnotationPresent(annotation)) {
            return true;
        }

        return false;
    }
}
