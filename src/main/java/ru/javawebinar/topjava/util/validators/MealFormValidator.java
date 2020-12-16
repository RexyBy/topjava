package ru.javawebinar.topjava.util.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@Component
public class MealFormValidator extends AbstractFormValidator {
    private MealService mealService;

    public MealFormValidator(MealService mealService) {
        this.mealService = mealService;
        messageCode = "error.mealExists";
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Meal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Meal targetMeal = (Meal) target;
        LocalDate targetMealDate = targetMeal.getDateTime() != null ? targetMeal.getDateTime().toLocalDate() : LocalDate.EPOCH;
        List<Meal> mealsOnThisDate = mealService.getBetweenInclusive(targetMealDate, targetMealDate, SecurityUtil.authUserId());
        for (Meal meal : mealsOnThisDate){
            if (meal.getDateTime().equals(targetMeal.getDateTime())){
                rejectValue(errors, "dateTime");
            }
        }
    }
}
