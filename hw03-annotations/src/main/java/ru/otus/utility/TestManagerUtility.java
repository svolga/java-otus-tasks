package ru.otus.utility;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.model.StatInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestManagerUtility {

    private TestManagerUtility() {
    }

    private static List<String> getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Method[] methods = clazz.getMethods();
        List<String> methodsWithAnnotation = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                methodsWithAnnotation.add(method.getName());
            }
        }
        return methodsWithAnnotation;
    }

    private static void invokeMethod(Object object, String method, StatInfo statInfo) {
        try {
            Method invMethod = object.getClass().getMethod(method);
            invMethod.invoke(object);
            if (statInfo != null) {
                statInfo.addSuccess(method);
            }
        } catch (Exception e) {
            if (statInfo != null) {
                statInfo.addFailed(method);
            }
            System.out.println("Failure to run method --> " + method + "; Exception --> " + e.getCause().getMessage());
        }
    }

    private static void invokeMethods(Object object, List<String> methods) {
        for (String method : methods) {
            invokeMethod(object, method, null);
        }
    }

    public static StatInfo runTest(Class<?> clazz) throws Exception {

        List<String> beforeMethods = getMethodsWithAnnotation(clazz, Before.class);
        List<String> afterMethods = getMethodsWithAnnotation(clazz, After.class);
        List<String> testMethods = getMethodsWithAnnotation(clazz, Test.class);
        Object object = clazz.getDeclaredConstructor().newInstance();
        StatInfo statInfo = new StatInfo();

        for (String testMethod : testMethods) {
            invokeMethods(object, beforeMethods);
            invokeMethod(object, testMethod, statInfo);
            invokeMethods(object, afterMethods);
        }
        return statInfo;
    }

}