package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        MealsUtil.meals.forEach(meal -> save(meal, (int) (Math.random() * 2 + 1)));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        repository.putIfAbsent(userId, new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> newMealMap = new HashMap<>();
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return getValidUserMeals(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return getValidUserMeals(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealsMap = getValidUserMeals(userId);
        return mealsMap.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getValidUserMeals(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());

    }

    @Override
    public List<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return getValidUserMeals(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    private Map<Integer, Meal> getValidUserMeals(int userId) {
        return repository.getOrDefault(userId, Collections.emptyMap());
    }
}

