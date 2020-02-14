package com.upp.service.model;

import java.util.List;

public interface IUserService {

    List<User> findAllUsers();
    void saveUser(User user);
    User findUserById(String id);
    User findUserByUsername(String username);

}
