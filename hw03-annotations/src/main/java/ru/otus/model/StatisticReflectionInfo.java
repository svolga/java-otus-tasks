package ru.otus.model;

import java.lang.reflect.Method;

public class StatisticReflectionInfo {
    private int success;
    private int failed;
    private final StringBuilder successMethods;
    private final StringBuilder failedMethods;

    public StatisticReflectionInfo() {
        successMethods = new StringBuilder();
        failedMethods = new StringBuilder();
    }

    public void addSuccess(Method method){
        successMethods.append(method.getName()).append("; ");
        success++;
    }

    public void addFailed(Method method){
        failedMethods.append(method.getName()).append("; ");
        failed++;
    }

    public StringBuilder getSuccessMethods() {
        return successMethods;
    }

    public StringBuilder getFailedMethods() {
        return failedMethods;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailed() {
        return failed;
    }

    public int getTotal(){
        return success + failed;
    }
}
