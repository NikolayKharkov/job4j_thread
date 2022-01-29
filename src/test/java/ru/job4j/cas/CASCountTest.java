package ru.job4j.cas;

import junit.framework.TestCase;
import org.junit.Test;
import ru.job4j.count.Count;
import ru.job4j.count.CountTest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CASCountTest {

    private class CasCount extends Thread {
        private final CASCount count;

        private CasCount(final CASCount count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final CASCount count = new CASCount();
        Thread first = new CASCountTest.CasCount(count);
        Thread second = new CASCountTest.CasCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2));
    }
}