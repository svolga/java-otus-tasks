package ru.otus.proxy;

public class Main {
    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createMyClass();
        testLogging.calculation(1);
        testLogging.calculation(21, 22);
        testLogging.calculation(31, 32, 33);
    }
}