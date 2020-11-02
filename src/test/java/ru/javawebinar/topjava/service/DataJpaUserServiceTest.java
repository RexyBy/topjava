package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Override
    @Test
    public void create() {
        super.create();
    }

    @Override
    @Test
    public void duplicateMailCreate() {
        super.duplicateMailCreate();
    }

    @Override
    @Test
    public void delete() {
        super.delete();
    }

    @Override
    @Test
    public void deleteNotFound() {
        super.deleteNotFound();
    }

    @Override
    @Test
    public void get() {
        super.get();
    }

    @Override
    @Test
    public void getNotFound() {
        super.getNotFound();
    }

    @Override
    @Test
    public void getByEmail() {
        super.getByEmail();
    }

    @Override
    @Test
    public void update() {
        super.update();
    }

    @Override
    @Test
    public void getAll() {
        super.getAll();
    }
}