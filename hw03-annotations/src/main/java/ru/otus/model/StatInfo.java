package ru.otus.model;

public class StatInfo {
    private int success;
    private int failed;
    private final StringBuilder successMethods;
    private final StringBuilder failedMethods;

    public StatInfo() {
        successMethods = new StringBuilder();
        failedMethods = new StringBuilder();
    }

    public void addSuccess(String method){
        successMethods.append(method).append("; ");
        success++;
    }

    public void addFailed(String method){
        failedMethods.append(method).append("; ");
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
