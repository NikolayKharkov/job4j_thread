package ru.job4j.usersstore;

import java.util.Objects;

public class User {
    private int id;
    private int amount;

    public static User of(int id, int amount) {
        User user = new User();
        user.id = id;
        user.amount = amount;
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}