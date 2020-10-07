package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private MealRepository mealRepository;
    private final int defaultCaloriesPerDay = 2000;
    private final Logger log = getLogger(MealServlet.class);

    @Override
    public void init() throws ServletException {
        mealRepository = new MemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        log.debug("Action is " + action);
        switch (action) {
            case "add":
                log.debug("forwarding to addMeal.jsp");
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            case "update":
                log.debug("forwarding to addMeal.jsp");
                Meal mealForUpdating = mealRepository.getById(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("dateTime", mealForUpdating.getDateTime());
                request.setAttribute("description", mealForUpdating.getDescription());
                request.setAttribute("calories", mealForUpdating.getCalories());
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            case "delete":
                int idForDeletion = Integer.parseInt(request.getParameter("id"));
                mealRepository.delete(idForDeletion);
                log.debug("redirecting to meals.jsp");
                response.sendRedirect("meals");
                break;
            default:
                List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(
                        mealRepository.getAll(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        defaultCaloriesPerDay);
                request.setAttribute("meals", mealsWithExcess);
                log.debug("forwarding to meals.jsp");
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer idForUpdating = request.getParameter("id").equals("") ? null : Integer.parseInt(request.getParameter("id"));
        LocalDateTime mealDateTime = LocalDateTime.parse(request.getParameter("mealDateTime"));
        String mealDescription = request.getParameter("mealDescription");
        int mealCalories = Integer.parseInt(request.getParameter("mealCalories"));
        if (idForUpdating == null) {
            log.debug("adding new meal");
            mealRepository.add(new Meal(LocalDateTime.parse(request.getParameter("mealDateTime")),
                    request.getParameter("mealDescription"),
                    Integer.parseInt(request.getParameter("mealCalories"))));
        } else  {
            log.debug("updating meal with id" + idForUpdating);
            mealRepository.update(new Meal(idForUpdating, mealDateTime, mealDescription, mealCalories));
        }
        log.debug("redirecting to meals.jsp");
        response.sendRedirect("meals");
    }
}
