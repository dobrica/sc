package com.upp.service.web;

import com.upp.service.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    List<User> users; //TODO: implement UserService registration, login, etc.

    UserController() {
        users = new ArrayList<User>();
        User user = new User();
        user.setUsername("dok123");
        user.setPassword("123");
        users.add(user);

        user.setUsername("demo");
        user.setPassword("demo");
        users.add(user);
    }

    @RequestMapping("/login")
    public boolean login(@RequestBody User user) {
        log.info("LOGIN");
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }
}
