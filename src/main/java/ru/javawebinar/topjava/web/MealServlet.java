package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealFilter;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext appCtx;
    private MealRestController controller;


    @Override
    public void init() throws ServletException {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.create(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().endsWith("filter")) {
            handleMealsWithFilter(request, response);
        } else {
            handleMeals(request, response);
        }

    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private void handleMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.authUserId()) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getTos(controller.getAll(), SecurityUtil.authUserCaloriesPerDay()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }

    }

    private void handleMealsWithFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealFilter filter = new MealFilter(
          request.getParameter("startDate").isEmpty() ? null : LocalDate.parse(request.getParameter("startDate")),
          request.getParameter("endDate").isEmpty() ? null : LocalDate.parse(request.getParameter("endDate")),
          request.getParameter("startTime").isEmpty() ? null : LocalTime.parse (request.getParameter("startTime")),
          request.getParameter("endTime").isEmpty() ? null : LocalTime.parse(request.getParameter("endTime"))
        );

        if (filter.isEmpty() == false){
            log.info("Filter is empty, redirecting to /meals");
            response.sendRedirect(request.getContextPath() + "/meals");
        }
        log.info("get filtered meals");
        request.setAttribute("meals", MealsUtil.filterByPredicate(
                controller.getAll(),
                SecurityUtil.authUserCaloriesPerDay(),
                meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), filter.getStartDate(), filter.getEndDate())
                        && DateTimeUtil.isBetweenHalfOpen(meal.getTime(), filter.getStartTime(), filter.getEndTime())));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}
