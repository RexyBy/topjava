package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface Repository {

    void addMeal(LocalDateTime dateTime, String description, int calories);

    boolean deleteMeal(int id);

    List<Meal> getAllMeals();

    void updateMeal(int id, LocalDateTime dateTime, String description, Integer calories);
}
