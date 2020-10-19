package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int FIRST_MEAL_ID = START_SEQ + 2;
    public static int nextId = FIRST_MEAL_ID;

    public static final Meal userBreakfast = new Meal(nextId++, LocalDateTime.of(2020, 10, 15, 10, 0), "Завтрак", 500);
    public static final Meal userLunch = new Meal(nextId++, LocalDateTime.of(2020, 10, 15, 14, 0), "Обед", 1000);
    public static final Meal userDinner = new Meal(nextId++, LocalDateTime.of(2020, 10, 15, 18, 0), "Ужин", 500);
    public static final Meal userSecondDayBreakfast = new Meal(nextId++, LocalDateTime.of(2020, 10, 16, 10, 0), "Завтрак", 500);
    public static final Meal userSecondDayLunch = new Meal(nextId++, LocalDateTime.of(2020, 10, 16, 15, 0), "Обед", 1000);
    public static final Meal userEdgeMeal = new Meal(nextId++, LocalDateTime.of(2020, 10, 16, 0, 0), "Еда на граничное значение", 120);
    public static final Meal userSecondDayDinner = new Meal(nextId++, LocalDateTime.of(2020, 10, 16, 22, 0), "Ужин", 500);

    public static final Meal adminBreakfast = new Meal(nextId++, LocalDateTime.of(2020, 10, 15, 10, 0), "Завтрак Админа", 400);
    public static final Meal adminLunch = new Meal(nextId++, LocalDateTime.of(2020, 10, 15, 14, 0), "Обед Админа", 1200);
    public static final Meal adminDinner = new Meal(nextId++, LocalDateTime.of(2020, 10, 15, 18, 0), "Ужин Админа", 300);
    public static final Meal adminSecondDayBreakfast = new Meal(nextId++, LocalDateTime.of(2020, 10, 16, 10, 0), "Завтрак Админа", 300);
    public static final Meal adminSecondDayLunch = new Meal(nextId++, LocalDateTime.of(2020, 10, 16, 15, 0), "Обед Админа", 900);
    public static final Meal adminSecondDaySnack = new Meal(nextId++, LocalDateTime.of(2020, 10, 16, 16, 0), "Перекус Админа", 200);
    public static final Meal adminSecondDayDinner = new Meal(nextId++, LocalDateTime.of(2020, 10, 16, 22, 0), "Ужин Админа", 1000);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, 10, 16, 12, 0), "Новая еда", 367);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userBreakfast);
        updated.setDateTime(LocalDateTime.of(2019, 9, 17, 15, 15));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(895);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
       assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
