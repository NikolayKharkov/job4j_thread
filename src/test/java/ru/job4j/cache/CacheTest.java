package ru.job4j.cache;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

    @Test
    public void whenSingleAddSuccessfully() {
        Cache cache = new Cache();
        Assert.assertTrue(cache.add(new Base(1, 1)));
    }

    @Test
    public void whenSingleAddAndUpdateSuccessfully() {
        Cache cache = new Cache();
        Base original = new Base(1, 1);
        Base updated = original;
        updated.setName("Updated");
        cache.add(original);
        Assert.assertTrue(cache.update(updated));
    }

    @Test(expected = OptimisticException.class)
    public void whenSingleAddAndUpdateButExpectError() {
        Cache cache = new Cache();
        Base original = new Base(1, 1);
        Base updated = new Base(1, 2);
        cache.add(original);
        Assert.assertTrue(cache.update(updated));
    }
}