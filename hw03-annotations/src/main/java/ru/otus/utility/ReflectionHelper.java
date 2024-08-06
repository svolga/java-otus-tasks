package ru.otus.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionHelper {

    private ReflectionHelper() {
    }

    public static void invokeMethod(Object object, Method method) {
        try {
            method.invoke(object);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<Method> getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toSet());
    }

}
