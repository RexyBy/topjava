package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class UserMealWithExcessCollector implements Collector<UserMeal, HashMap<UserMeal, CaloriesConsumption>, List<UserMealWithExcess>> {
    private HashMap<LocalDate, CaloriesConsumption> caloriesConsumptionPerDay = new HashMap<>();
    private int caloriesPerDay;
    private LocalTime startTime;
    private LocalTime endTime;


    public UserMealWithExcessCollector(int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        super();
        this.caloriesPerDay = caloriesPerDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Supplier<HashMap<UserMeal, CaloriesConsumption>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<HashMap<UserMeal, CaloriesConsumption>, UserMeal> accumulator() {
        return (map, meal) -> {
            LocalDateTime mealDateTime = meal.getDateTime();
            if (!caloriesConsumptionPerDay.containsKey(mealDateTime.toLocalDate()))
                caloriesConsumptionPerDay.put(mealDateTime.toLocalDate(), new CaloriesConsumption());
            CaloriesConsumption caloriesConsumption = caloriesConsumptionPerDay.get(mealDateTime.toLocalDate());
            caloriesConsumption.addCalories(meal.getCalories());
            if (caloriesConsumption.getCalories() > caloriesPerDay)
                caloriesConsumption.setExcess(true);

            map.put(meal, caloriesConsumption);
        };
    }

    @Override
    public BinaryOperator<HashMap<UserMeal, CaloriesConsumption>> combiner() {
        return (l, r) -> {
            l.putAll(r);
            return l;
        };
    }

    @Override
    public Function<HashMap<UserMeal, CaloriesConsumption>, List<UserMealWithExcess>> finisher() {
        return map -> {
            List<UserMealWithExcess> listForReturn = new ArrayList<>();
            for (Map.Entry<UserMeal, CaloriesConsumption> nextEntry : map.entrySet()) {
                UserMeal nextMeal = nextEntry.getKey();
                if (TimeUtil.isBetweenHalfOpen(nextMeal.getDateTime().toLocalTime(), startTime, endTime))
                    listForReturn.add(
                            new UserMealWithExcess(nextMeal.getDateTime(),
                                    nextMeal.getDescription(),
                                    nextMeal.getCalories(),
                                    nextEntry.getValue())
                    );
            }
            return listForReturn;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }
}
