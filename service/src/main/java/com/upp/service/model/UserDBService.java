package com.upp.service.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDBService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserDBService(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll().stream()
                .map( u -> new User(
                        u.getId(),
                        u.getEmail(),
                        u.getUsername(),
                        u.getPassword(),
                        u.getIsReviewer(),
                        u.getIsEditor(),
                        u.getSubscriptionStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getIsReviewer(),
                user.getIsEditor(),
                user.getSubscriptionStatus()));
    }

    @Override
    public User findUserById(String id) {
        var users = findAllUsers();
        for (User u: users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        var users = findAllUsers();
        for (User u: users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

}
