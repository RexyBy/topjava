package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MemoryRepository;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.MockUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


public class MealServlet extends HttpServlet {
    private static Repository repository = MemoryRepository.getRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("update")) {
            request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            int idForDeletion = Integer.parseInt(request.getParameter("id"));
            repository.deleteMeal(idForDeletion);
            response.sendRedirect("meals");
        } else {
            List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(
                    repository.getAllMeals(),
                    MockUtil.getDefaultStartTime(),
                    MockUtil.getDefaultEndTime(),
                    MockUtil.getDefaultCaloriesPerDay());
            request.setAttribute("meals", mealsWithExcess);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        LocalDateTime mealDateTime =
                request.getParameter("mealDateTime").equals("") ? null : LocalDateTime.parse(request.getParameter("mealDateTime"));
        String mealDescription = request.getParameter("mealDescription");
        Integer mealCalories =
                request.getParameter("mealCalories").equals("") ? null : Integer.parseInt(request.getParameter("mealCalories"));
        if (action.equalsIgnoreCase("add")
                && mealDateTime != null
                && mealDescription != null
                && mealCalories != null) {
            repository.addMeal(LocalDateTime.parse(request.getParameter("mealDateTime")),
                    request.getParameter("mealDescription"),
                    Integer.parseInt(request.getParameter("mealCalories")));
        } else if (action.equalsIgnoreCase("update")) {
            int idForUpdating = Integer.parseInt(request.getParameter("id"));
            repository.updateMeal(idForUpdating, mealDateTime, mealDescription, mealCalories);
        }
        response.sendRedirect("meals");
    }
}
