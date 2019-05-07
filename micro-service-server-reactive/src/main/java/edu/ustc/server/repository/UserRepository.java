package edu.ustc.server.repository;

import edu.ustc.server.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by colddew on 2019/5/7.
 */
@Repository
public class UserRepository {

    private static final ConcurrentMap<Integer, User> userRepository = new ConcurrentHashMap<>();

    private AtomicInteger idGenerator = new AtomicInteger();

    public User save(User user) {

        int id = idGenerator.incrementAndGet();
        user.setId(id);

        userRepository.put(id, user);

        return user;
    }

    public Collection<User> list() {
        return userRepository.values();
    }
}
