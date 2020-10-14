package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealFilter;

import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, int userId);

    // false if not found meal with appropriate ID in user meals
    boolean delete(int id, int userId);

    // null if specified id not found in user meals
    Meal get(int id, int userId);

    //null if user not found
    List<Meal> getAll(int userId);

    //empty list if not found any meals for user with required filter
    List<Meal> getFilteredByDate(MealFilter filter, int userId);
}
