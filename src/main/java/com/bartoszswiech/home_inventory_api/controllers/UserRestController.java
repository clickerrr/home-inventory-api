package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.services.RoomService;
import com.bartoszswiech.home_inventory_api.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {


    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public boolean loginUser(@RequestBody User userQueried) {
        return userService.verifyUser(userQueried.getEmail(), userQueried.getEncodedPassword());
    }

}
