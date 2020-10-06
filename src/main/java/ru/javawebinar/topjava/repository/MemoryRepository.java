package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryRepository implements Repository {
    private static final AtomicInteger id = new AtomicInteger(1);
    private final List<Meal> meals = new CopyOnWriteArrayList<>();


    private MemoryRepository() {
        mealsInit();
    }

    private void mealsInit() {
        meals.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }


    private static class repositoryHolder {
        private static final MemoryRepository REPOSITORY = new MemoryRepository();
    }

    public static MemoryRepository getRepository() {
        return repositoryHolder.REPOSITORY;
    }

    @Override
    public List<Meal> getAllMeals() {
        return meals;
    }

    @Override
    public void addMeal(LocalDateTime dateTime, String description, int calories) {
        meals.add(new Meal(id.incrementAndGet(), dateTime, description, calories));
    }

    @Override
    public boolean deleteMeal(int id) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId() == id) {
                meals.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateMeal(int id, LocalDateTime dateTime, String description, Integer calories) {
        Meal oldMeal = null;
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                oldMeal = meal;
                break;
            }
        }
        if (oldMeal != null) {
            deleteMeal(id);
            meals.add(new Meal(id,
                    dateTime == null ? oldMeal.getDateTime() : dateTime,
                    description.equals("") ? oldMeal.getDescription() : description,
                    calories == null ? oldMeal.getCalories() : calories));
        }
    }
}
