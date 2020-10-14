package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;


import java.util.Arrays;
import java.util.List;


public class SpringMain {
    public static final List<User> users = Arrays.asList(
            new User(null, "John", "john@mail.ru", "qwerty", Role.USER),
            new User(null, "Mike", "mike@gmail.com", "asdfg", Role.USER),
            new User(null, "Anna", "an.na@yahoo.com", "12345566", Role.USER),
            new User(null, "Jane", "jane.smith@bk.ru", "qwerty", Role.USER),
            new User(null, "Vasyan", "vasyan@yandex.ru", "YECgAa", Role.ADMIN)
    );

    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            MealRepository mealRepository = appCtx.getBean(MealRepository.class);
            mealRepository.get(5, 1);
        }

    }
}
