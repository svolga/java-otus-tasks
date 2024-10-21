package ru.otus.core.repository;

public class DataTemplateException extends RuntimeException {
    public DataTemplateException(Exception ex) {
        super(ex);
    }

    public DataTemplateException(String msg) {
        super(msg);
    }
}
