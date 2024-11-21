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
        while (isShouldStart == isShouldStartFirst) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        logger.info("{}: {}", Thread.currentThread().getName(), number);
        isShouldStart = !isShouldStart;
        sleep();
        notifyAll();
    }


}
