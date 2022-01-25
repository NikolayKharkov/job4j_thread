package ru.job4j.usersstore;

import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStore {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public boolean add(User user) {
        return users.putIfAbsent(user.getId(), User.of(user.getId(), user.getAmount())) != null;
    }

    public boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    public boolean update(User user) {
        return users.computeIfPresent(user.getId(), (k, v) -> User.of(user.getId(), user.getAmount())) != null;
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (fromId != toId
                && amount > 0
                && users.containsKey(fromId)
                && users.containsKey(toId)
                && users.get(fromId).getAmount() >= amount) {
            User from = User.of(users.get(fromId).getId(), users.get(fromId).getAmount() - amount);
            User to = User.of(users.get(toId).getId(), users.get(toId).getAmount() + amount);
            result = update(from) && update(to);
        }
        return result;
    }
}
