package ru.otus;

import ru.otus.model.StatisticReflectionInfo;
import ru.otus.utility.TestManagerUtility;

import ru.otus.test.CustomerTest;

public class Main {

    public static void main(String[] args) {
        new Main().run(args);
    }

    private void run(String[] args) {
        try {
            StatisticReflectionInfo statInfo = TestManagerUtility.runTest(CustomerTest.class);
            print(statInfo, CustomerTest.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void print(StatisticReflectionInfo statInfo, Class<?> clazz) {
        System.out.println("Test results for class --> " + clazz.getName());
        System.out.println("Success tests for methods: " + statInfo.getSuccessMethods());
        System.out.println("Count of success tests: " + statInfo.getSuccess());
        System.out.println("Failed tests for methods: " + statInfo.getFailedMethods());
        System.out.println("Count of failed tests: " + statInfo.getFailed());
        System.out.println("Total tests: " + statInfo.getTotal());
    }
}