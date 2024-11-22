package ru.otus;

public class Task implements Runnable{

    private final TaskPrinter printer;
    private final int min;
    private final int max;
    private final boolean isRunningFirst;

    public Task(TaskPrinter printer, boolean isRunningFirst, int min, int max) {
        this.printer = printer;
        this.isRunningFirst = isRunningFirst;
        this.min = min;
        this.max = max;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            up();
            down();
        }
    }

    private void up() {
        for (int i = min; i <= max; i++) {
            printer.runWork(i, isRunningFirst);
        }
    }

    private void down() {
        for (int i = max - 1; i > min; i--) {
            printer.runWork(i, isRunningFirst);
        }
    }

}
