package ru.job4j.pools;

import org.junit.Assert;
import org.junit.Test;

public class PararrelSearchTest {

    @Test
    public void whenArrayBelowTenAndFind() {
        String[] objects = {"0", "1", "2", "3", "4", "5", "6", "7"};
        Assert.assertEquals(4, PararrelSearch.findTarget(objects, "4"));
    }

    @Test
    public void whenArrayBelowTenAndNotFind() {
        String[] objects = {"0", "1", "2", "3", "4", "5", "6", "7"};
        Assert.assertEquals(-1, PararrelSearch.findTarget(objects, "fgfgfg"));
    }

    @Test
    public void whenArrayMoreTenAndFind() {
        String[] objects = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        Assert.assertEquals(13, PararrelSearch.findTarget(objects, "13"));
    }
    @Test
    public void whenArrayMoreTenAndNotFind() {
        String[] objects = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        Assert.assertEquals(-1, PararrelSearch.findTarget(objects, "-14"));
    }
}