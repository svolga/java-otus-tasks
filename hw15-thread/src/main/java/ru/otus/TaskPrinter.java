package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskPrinter {

    private volatile boolean isShouldStart;
    private final Logger logger = LoggerFactory.getLogger(TaskPrinter.class);

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    synchronized void runWork(int number, boolean isShouldStartFirst) {
        try {
            while (isShouldStart == isShouldStartFirst) {
                wait();
            }

            logger.info("{}: {}", Thread.currentThread().getName(), number);
            isShouldStart = !isShouldStart;
            sleep();
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
