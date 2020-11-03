package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public final class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getWithMeals() {
        User actual = service.getWithMeals(USER_ID);
        User expected = getUserWithMeals();
        USER_MATCHER.assertMatch(actual, expected);
        MealTestData.MEAL_MATCHER.assertMatch(actual.getMeals(), expected.getMeals());
    }

    @Test
    public void getWithMealsNotFound(){
        assertThrows(NotFoundException.class, () -> service.getWithMeals(NOT_FOUND));
    }
}