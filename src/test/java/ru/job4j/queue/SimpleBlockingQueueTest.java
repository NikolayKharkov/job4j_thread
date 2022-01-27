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
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i != 2; i++) {
                queue.poll();
            }
        });
        maker.start();
        consumer.start();
        maker.join();
        consumer.join();
        assertThat(queue.poll(), is(2));
    }
}