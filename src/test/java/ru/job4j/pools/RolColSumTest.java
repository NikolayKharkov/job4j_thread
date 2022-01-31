package ru.job4j.pools;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RolColSumTest {

    @Test
    public void whenSumSerialMethod() {
        int[][] array = {
                {4, 2, 3},
                {7, 1, 5},
                {3, 5, 6}
        };
        int rowExpected = 13;
        int colExpected = 8;
        RolColSum.Sums[] sums = RolColSum.sum(array);
        assertThat(sums[1].getRowSum(), is(rowExpected));
        assertThat(sums[1].getColSum(), is(colExpected));
    }

    @Test
    public void whenSumSyncMethod() throws ExecutionException, InterruptedException{
        int[][] array = {
                {4, 2, 3},
                {7, 1, 5},
                {3, 5, 6}
        };
        int rowExpected = 13;
        int colExpected = 8;
        RolColSum.Sums[] sums = RolColSum.asyncSum(array);
        assertThat(sums[1].getRowSum(), is(rowExpected));
        assertThat(sums[1].getColSum(), is(colExpected));
    }
}