package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(FIRST_MEAL_ID, USER_ID);
        MealTestData.assertMatch(meal, userBreakfast);
    }

    @Test
    public void getOtherUserFood() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(FIRST_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() throws Exception {
        service.delete(FIRST_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(FIRST_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteOtherUserFood() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(FIRST_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> actualUserMeals = service.getAll(USER_ID);
        MealTestData.assertMatch(actualUserMeals, userBreakfast, userLunch, userDinner,
                userSecondDayBreakfast, userSecondDayLunch, userSecondDaySnack,
                userSecondDayDinner);
        List<Meal> actualAdminMeals = service.getAll(ADMIN_ID);
        MealTestData.assertMatch(actualAdminMeals, adminBreakfast, adminLunch, adminDinner,
                adminSecondDayBreakfast, adminSecondDayLunch, adminSecondDaySnack,
                adminSecondDayDinner);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(FIRST_MEAL_ID, USER_ID), updated);
    }

    @Test
    public void updateOtherUserFood() throws Exception {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void getBetweenInclusive() throws Exception {
        List<Meal> meals = service.getBetweenInclusive(userBreakfast.getDateTime().toLocalDate(), userBreakfast.getDateTime().toLocalDate(), USER_ID);
        assertMatch(meals, userBreakfast, userDinner, userLunch);
    }
}