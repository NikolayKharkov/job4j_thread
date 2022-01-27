package ru.job4j.queue;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void whenMakerAddThreeElementsConsumerTakeTwoElements() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread maker = new Thread(() -> {
            for (int i = 0; i != 3; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i != 2; i++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        maker.start();
        consumer.start();
        maker.join();
        consumer.join();
        assertThat(queue.poll(), is(2));
    }
}