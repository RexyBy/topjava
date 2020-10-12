package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {
    public static final List<User> users = Arrays.asList(
           new User(null, "John", "john@mail.ru", "qwerty", Role.USER),
           new User(null, "Mike", "mike@gmail.com", "asdfg", Role.USER),
           new User(null, "Anna", "an.na@yahoo.com", "12345566", Role.USER),
           new User(null, "Jane", "jane.smith@bk.ru", "qwerty", Role.USER),
           new User(null, "Vasyan", "vasyan@yandex.ru", "YECgAa", Role.ADMIN)
    );
}
