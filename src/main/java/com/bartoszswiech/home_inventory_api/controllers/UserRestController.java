package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.JWTRefreshRequestBody;
import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.exceptions.UserAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.interfaces.UserView;
import com.bartoszswiech.home_inventory_api.services.RoomService;
import com.bartoszswiech.home_inventory_api.services.UserService;
import org.apache.coyote.Response;
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
    public ResponseEntity<String> createUser(@RequestBody User newUser) {
        try {
            if(userService.createUser(newUser)) {
                return ResponseEntity.ok("User successfully created");
            }
            else {
                return ResponseEntity.internalServerError().body("User not created");
            }
        }
        catch(UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }

    }

    @GetMapping("/refreshToken")
    public String refresh() {
        return userService.getNewToken();
    }

}
