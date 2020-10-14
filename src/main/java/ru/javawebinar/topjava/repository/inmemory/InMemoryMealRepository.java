package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealFilter;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
      //  MealsUtil.meals.forEach(meal -> save(meal, (int) (Math.random() * 2 + 1)));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> newMealMap = new HashMap<>();
            newMealMap.put(meal.getId(), meal);
            repository.merge(userId, newMealMap, (oldVal, newVal) -> {
                oldVal.putAll(newVal);
                return oldVal;
            });
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
        List<Meal> allUserMeals = new ArrayList<>(getValidUserMeals(userId).values());
        compareByDateInReverseOrder(allUserMeals);
        return allUserMeals;
    }

    @Override
    public List<Meal> getFilteredByDate(MealFilter filter, int userId) {
        List<Meal> userMealsFilteredByDate = getValidUserMeals(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), filter.getStartDate(), filter.getEndDate()))
                .collect(Collectors.toList());
        compareByDateInReverseOrder(userMealsFilteredByDate);
        return userMealsFilteredByDate;
    }

    private Map<Integer, Meal> getValidUserMeals(int userId) {
        return repository.getOrDefault(userId, Collections.EMPTY_MAP) == null ? new HashMap<>() : repository.getOrDefault(userId, Collections.EMPTY_MAP);
    }

    private void compareByDateInReverseOrder(List<Meal> meals){
        Comparator<Meal> dateComparator = (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime());
        meals.sort(dateComparator);
    }
}

