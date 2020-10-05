package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MemoryRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.MockUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(
                MemoryRepository.getRepository().getMeals(),
                MockUtil.getDefaultStartTime(),
                MockUtil.getDefaultEndTime(),
                MockUtil.getDefaultCaloriesPerDay());
        request.setAttribute("meals", mealsWithExcess);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
