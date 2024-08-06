package ru.otus.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.model.StatisticReflectionInfo;

import java.lang.reflect.Method;
import java.util.Set;

public class TestManagerUtility {
    private static final Logger logger = LoggerFactory.getLogger(TestManagerUtility.class);

    private TestManagerUtility() {
    }

    public static StatisticReflectionInfo runTest(Class<?> clazz) throws Exception {

        StatisticReflectionInfo statisticReflectionInfo = new StatisticReflectionInfo();
        Set<Method> beforeMethods = ReflectionHelper.getMethodsWithAnnotation(clazz, Before.class);
        Set<Method> afterMethods = ReflectionHelper.getMethodsWithAnnotation(clazz, After.class);
        Set<Method> testMethods = ReflectionHelper.getMethodsWithAnnotation(clazz, Test.class);

        for (Method testMethod : testMethods) {
            Object object = clazz.getDeclaredConstructor().newInstance();

            if (invokeBeforeMethods(object, beforeMethods, testMethod, statisticReflectionInfo)) {
                invokeTestMethod(object, testMethod, statisticReflectionInfo);
            }

            invokeMethods(object, afterMethods);
        }

        return statisticReflectionInfo;
    }

    private static boolean invokeBeforeMethods(Object object, Set<Method> methods, Method testMethod,
                                               StatisticReflectionInfo statisticReflectionInfo) {
        for (Method method : methods) {
            try {
                ReflectionHelper.invokeMethod(object, method);
            } catch (Exception e) {
                logMethodException(method, e);
                statisticReflectionInfo.addFailed(testMethod);
                return false;
            }
        }
        return true;
    }

    private static void invokeTestMethod(Object object, Method testMethod, StatisticReflectionInfo statisticReflectionInfo) {
        try {
            ReflectionHelper.invokeMethod(object, testMethod);
            statisticReflectionInfo.addSuccess(testMethod);
        } catch (Exception e) {
            logMethodException(testMethod, e);
            statisticReflectionInfo.addFailed(testMethod);
        }
    }

    private static void invokeMethods(Object object, Set<Method> methods) {
        methods.forEach(method -> ReflectionHelper.invokeMethod(object, method));
    }

    private static void logMethodException(Method method, Exception e) {
        logger.error("Failure to run method --> {}; Exception --> {}", method.getName(), e.getCause().getCause().getMessage());
    }

}