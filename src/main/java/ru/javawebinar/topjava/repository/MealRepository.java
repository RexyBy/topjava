package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {

    Meal add(Meal newMeal);

    boolean delete(int id);

    List<Meal> getAll();

    Meal update(Meal newMeal);

    Meal getById(int id);
}
