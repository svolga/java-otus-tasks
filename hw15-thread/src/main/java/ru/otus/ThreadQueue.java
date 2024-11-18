package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class ThreadQueue extends Thread {

    private final BlockingQueue<String> in;
    private final BlockingQueue<String> out;
    private static final Logger logger = LoggerFactory.getLogger(ThreadQueue.class);

    private boolean up = true;
    private final int min = 1;
    private final int max = 10;

    public ThreadQueue(BlockingQueue<String> in, BlockingQueue<String> out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            Up();
            Down();
        }
    }

    private void Up() {
        if (up) {
            for (int i = min; i <= max; i++) {
                print(i);
            }
            up = false;
        }
    }

    private void Down() {
        if (!up) {
            for (int i = max - 1; i > min; i--) {
                print(i);
            }
            up = true;
        }
    }

    private void print(int i) {
        try {
            sleep();
            in.take();
            logger.info("{} : Number: {}", Thread.currentThread().getName(), i);
            out.put("turn");
        } catch (InterruptedException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            logger.info("InterruptedException in sleep");
            Thread.currentThread().interrupt();
        }
    }

}