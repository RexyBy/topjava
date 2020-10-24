package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        //meal.setUser(getUserRefByID(userId));
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user=:user");
        return query.setParameter("id", id).setParameter("user", getUserRefByID(userId)).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createQuery("SELECT Meal FROM Meal m WHERE m.id=:id AND m.user=:user")
                .setParameter("id", id)
                .setParameter("user", getUserRefByID(userId))
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("SELECT Meal FROM Meal m WHERE m.user=:user")
                .setParameter("user", getUserRefByID(userId))
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createQuery(
                "SELECT Meal FROM Meal m WHERE m.user=:user AND m.dateTime>=:dateTimeMin AND m.dateTime<:dateTimeMax ORDER BY m.dateTime DESC")
                .setParameter("user", getUserRefByID(userId))
                .setParameter("dateTimeMin", startDateTime)
                .setParameter("dateTimeMax", endDateTime)
                .getResultList();
    }

    private User getUserRefByID(int userId) {
        return em.getReference(User.class, userId);
    }
}