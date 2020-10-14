package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.SpringMain;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(0);

    public InMemoryUserRepository() {
        SpringMain.users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(nextId.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> allUsers = new ArrayList<>(repository.values());
        Comparator<User> nameComparator = (o1, o2) -> {
            if (o1.getName().compareTo(o2.getName()) == 0)
                return o1.getEmail().compareTo(o2.getEmail());
            return o1.getName().compareTo(o2.getName());
        };
        allUsers.sort(nameComparator);
        return allUsers;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
