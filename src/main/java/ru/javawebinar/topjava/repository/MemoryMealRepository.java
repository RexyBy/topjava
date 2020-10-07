package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealRepository implements MealRepository {
    private static final AtomicInteger id = new AtomicInteger(1);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MemoryMealRepository() {
        mealsInit();
    }

    private void mealsInit() {
        add(new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        add(new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        add(new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        add(new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        add(new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> getAll() {
        return new CopyOnWriteArrayList<>(meals.values());
    }

    @Override
    public Meal add(Meal newMeal) {
        meals.put(id.getAndIncrement(), newMeal);
        return newMeal;
    }

    @Override
    public boolean delete(int id) {
        return meals.remove(id) == null;
    }

    @Override
    public Meal update(Meal newMeal) {
        meals.put(newMeal.getId(), newMeal);
        return newMeal;
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    public static int getNextId() {
        return id.get();
    }
}
