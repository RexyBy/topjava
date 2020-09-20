package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//       List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        System.out.println(filterByOneCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesForEachDate = calculateCaloriesForEachDay(meals);

        List<UserMealWithExcess> userMealsWithExcess = new ArrayList<>();
        for (UserMeal nextUserMeal : meals) {
            LocalDateTime mealDateTime = nextUserMeal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(mealDateTime.toLocalTime(), startTime, endTime)) {
                String mealDescription = nextUserMeal.getDescription();
                int mealCalories = nextUserMeal.getCalories();
                boolean excess = caloriesForEachDate.getOrDefault(mealDateTime.toLocalDate(), 0) > caloriesPerDay;
                userMealsWithExcess.add(new UserMealWithExcess(mealDateTime,
                        mealDescription,
                        mealCalories,
                        excess));
            }
        }
        return userMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesForEachDate = calculateCaloriesForEachDay(meals);

        return meals.stream()
                .filter(nextMeal -> TimeUtil.isBetweenHalfOpen(nextMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(nextMeal ->
                        new UserMealWithExcess(
                                nextMeal.getDateTime(),
                                nextMeal.getDescription(),
                                nextMeal.getCalories(),
                                caloriesForEachDate.getOrDefault(nextMeal.getDateTime().toLocalDate(), 0) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filterByOneCycle(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealsWithExcess = new ArrayList<>();
        Map<LocalDate, CaloriesConsumption> caloriesConsumptionPerDay = new HashMap<>();
        for (UserMeal nextMeal : meals) {
            LocalDateTime mealDateTime = nextMeal.getDateTime();
            if (!caloriesConsumptionPerDay.containsKey(mealDateTime.toLocalDate()))
                caloriesConsumptionPerDay.put(mealDateTime.toLocalDate(), new CaloriesConsumption());
            CaloriesConsumption caloriesConsumption = caloriesConsumptionPerDay.get(mealDateTime.toLocalDate());
            caloriesConsumption.addCalories(nextMeal.getCalories());
            if (caloriesConsumption.getCalories() > caloriesPerDay)
                caloriesConsumption.setExcess(true);

            if (TimeUtil.isBetweenHalfOpen(mealDateTime.toLocalTime(), startTime, endTime)) {
                userMealsWithExcess.add(new UserMealWithExcess(mealDateTime,
                        nextMeal.getDescription(),
                        nextMeal.getCalories(),
                        caloriesConsumption));
            }
        }
        return userMealsWithExcess;
    }

    private static Map<LocalDate, Integer> calculateCaloriesForEachDay(List<UserMeal> meals) {
        Map<LocalDate, Integer> caloriesForEachDate = new HashMap<>();
        meals.forEach(nextMeal -> caloriesForEachDate.merge(
                nextMeal.getDateTime().toLocalDate(),
                nextMeal.getCalories(),
                Integer::sum));
        return caloriesForEachDate;
    }
}
