package ru.job4j.usersstore;

import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStore {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) != null;
    }

    public boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public boolean update(User user) {
        return users.computeIfPresent(user.getId(), (k, v) -> user) != null;
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User from = users.get(fromId);
        User to = users.get(toId);
        if (from != null && to != null && from.getAmount() >= amount) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            result = true;
        }
        return result;
    }
}
