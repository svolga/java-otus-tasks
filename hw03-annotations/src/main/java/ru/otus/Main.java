package ru.otus;

import ru.otus.model.StatInfo;
import ru.otus.utility.TestManagerUtility;

import ru.otus.test.CustomerTest;

public class Main {

    public static void main(String[] args) {
        new Main().run(args);
    }

    private void run(String[] args) {
        try {
            Class<?> clazz = CustomerTest.class;
            StatInfo statInfo = TestManagerUtility.runTest(clazz);
            print(statInfo, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void print(StatInfo statInfo, Class<?> clazz) {
        System.out.println("Test results for class --> " + clazz.getName());
        System.out.println("Success tests for methods: " + statInfo.getSuccessMethods());
        System.out.println("Count of success tests: " + statInfo.getSuccess());
        System.out.println("Failed tests for methods: " + statInfo.getFailedMethods());
        System.out.println("Count of failed tests: " + statInfo.getFailed());
        System.out.println("Total tests: " + statInfo.getTotal());
    }
}