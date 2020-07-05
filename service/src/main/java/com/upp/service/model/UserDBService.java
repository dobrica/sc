package com.upp.service.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
                        u.getFistname(),
                        u.getLastname(),
                        u.getEmail(),
                        u.getUsername(),
                        u.getPassword(),
                        u.getCity(),
                        u.getState(),
                        u.getIsReviewer(),
                        u.getIsEditor(),
                        u.getSubscriptionStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(new UserEntity(
                user.getId(),
                user.getFistname(),
                user.getLastname(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getCity(),
                user.getState(),
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

    public List<User> getUsersByRole(String role) {
        List<User> usersWithRole = new ArrayList<>();
        List<User> allUsers = findAllUsers();
        for (User u: allUsers) {
            if (u.getIsEditor() && role.equals("EDITOR")) {
                usersWithRole.add(u);
            }
            if (u.getIsReviewer() && role.equals("REVIEWER")) {
                usersWithRole.add(u);
            }
        }
        return usersWithRole;
    }

    public List<String> usernamesWithRole(String role){
        List<String> usernames = new ArrayList<>();
        for (User u: getUsersByRole(role)) {
            usernames.add(u.getUsername());
        }
        return usernames;
    }

    public User getRandomEditorExcludingMagazineMainEditor(User mainEditor){
        List<User> availableEditors = new ArrayList<>();
        for (User u: findAllUsers()) {
            if (u.getIsEditor() && !u.getUsername().equals(mainEditor.getUsername())){
                availableEditors.add(u);
            }
        }
        Random r = new Random();
        int randomIndex = r.nextInt(availableEditors.size());
        return availableEditors.get(randomIndex);
    }

}
