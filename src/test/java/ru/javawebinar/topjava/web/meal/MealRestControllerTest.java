package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MealRestControllerTest extends AbstractControllerTest {
    private final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    private MealService service;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MEAL_TO_MATCHER.contentJson(MealTestData.getMealsTo()));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .queryParam("startTime",MealTestData.START_TIME )
                .queryParam("endTime",MealTestData.END_TIME )
                .queryParam("startDate",MealTestData.START_DATE )
                .queryParam("endDate",MealTestData.END_DATE ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MEAL_TO_MATCHER.contentJson(MealTestData.getMealsToBetween()));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MealTestData.MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MEAL_MATCHER.contentJson(MealTestData.meal1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MealTestData.MEAL1_ID))
                .andExpect(status().isNoContent());
        MealTestData.MEAL_MATCHER.assertMatch(service.getAll(UserTestData.USER_ID),
                MealTestData.mealsWithoutDeletedItem);
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal createdMeal = TestUtil.readFromJson(action, Meal.class);
        int newMealID = createdMeal.getId();
        newMeal.setId(newMealID);

        MealTestData.MEAL_MATCHER.assertMatch(newMeal, createdMeal);
        MealTestData.MEAL_MATCHER.assertMatch(service.get(newMealID, UserTestData.USER_ID), newMeal);
    }

    @Test
    void update() throws Exception {
        Meal updatedMeal = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MealTestData.MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedMeal)))
                .andExpect(status().isNoContent());

        MealTestData.MEAL_MATCHER.assertMatch(
                service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID),
                updatedMeal);
    }
}