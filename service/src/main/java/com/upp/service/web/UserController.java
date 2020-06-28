package com.upp.service.web;

import com.upp.service.model.LoginRequest;
import com.upp.service.model.User;
import com.upp.service.security.TokenUtils;
import com.upp.service.security.UserTokenState;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class UserController {

    @Autowired
    public TokenUtils tokenUtils;

    @Autowired
    public AuthenticationManager manager;

    @Autowired
    private IdentityService identityService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> login(@RequestBody LoginRequest loginRequest) {
        final Authentication authentication = manager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        User user =  (User) authentication.getPrincipal();
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String jwt = tokenUtils.generateToken(user.getUsername());
        Long expiresIn = 3600L;

        return ResponseEntity.ok(new UserTokenState(jwt,expiresIn));
    }

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        identityService.clearAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
