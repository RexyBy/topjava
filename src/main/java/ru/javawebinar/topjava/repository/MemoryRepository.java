package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryRepository {
    private static volatile long id = 1;
    private List<Meal> meals = new CopyOnWriteArrayList<>();


    private MemoryRepository() {
        mealsInit();
    }

    private void mealsInit() {
        addMeal(new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        addMeal(new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        addMeal(new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        addMeal(new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        addMeal(new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        addMeal(new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        addMeal(new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private void addMeal(Meal meal) {
        meals.add(meal);
        synchronized (MemoryRepository.class){
            id++;
        }
    }

    private static class repositoryHolder {
        private static final MemoryRepository REPOSITORY = new MemoryRepository();
    }

    public static MemoryRepository getRepository(){
        return repositoryHolder.REPOSITORY;
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
