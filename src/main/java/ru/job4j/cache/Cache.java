package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        if (stored.getVersion() != model.getVersion()) {
            throw new OptimisticException("Versions are not equal");
        }
        Base updated = new Base(stored.getId(), stored.getVersion() + 1);
        updated.setName(model.getName());
        return memory.computeIfPresent(stored.getId(), (k, v) -> updated) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}