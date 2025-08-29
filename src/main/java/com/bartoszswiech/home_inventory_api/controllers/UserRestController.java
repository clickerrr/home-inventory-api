package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.JWTRefreshRequestBody;
import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.services.RoomService;
import com.bartoszswiech.home_inventory_api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User userQueried) {
        System.out.printf("uname: %s, email: %s, pass: %s\n",userQueried.getUsername(), userQueried.getEmail(), userQueried.getPassword());

        return userService.verifyUser(userQueried);

    }

    @PostMapping("/register")
    public boolean createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @GetMapping("/refreshToken")
    public String refresh() {
        return userService.getNewToken();
    }

}
