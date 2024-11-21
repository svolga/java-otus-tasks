package ru.otus;

public class Main {

    public static void main(String[] args) {
        var printer = new TaskPrinter();
        new Thread(new Task(printer, true, 1, 10)).start();
        new Thread(new Task(printer, false, 1, 10)).start();
    }
}
