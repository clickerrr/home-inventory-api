package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.exceptions.UserAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.UserNotFoundException;
import com.bartoszswiech.home_inventory_api.interfaces.UserView;
import com.bartoszswiech.home_inventory_api.services.HouseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/houses")
public class HouseRestController {

    private final HouseService houseService;

    public HouseRestController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public List<House> getAllHouses() {
        return houseService.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createHouse(@RequestBody House newHouse) {
        try {
            houseService.createHouse(newHouse);
            return ResponseEntity.ok("House successfully created");
        }
        catch( Exception ex) {
            System.out.println(ex);
            return ResponseEntity.badRequest().body("Bad request");
        }

    }

    @GetMapping("/{id}")
    public House getHouse(@PathVariable Long id) {
        return houseService.findById(id);
    }

    @PutMapping("/{id}")
    public House updateHouse(@RequestBody House updatedHouse, @PathVariable Long id) {
        return houseService.update(id, updatedHouse);
    }

    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable Long id) {
        houseService.deleteById(id);
    }

    @GetMapping("/userDetails")
    public ResponseEntity<List<UserView>> getUserDetailsOfHouse(@RequestParam Long houseId) {
        return ResponseEntity.ok(houseService.getUserDetails(houseId));
    }

    @PostMapping("/userDetails")
    public ResponseEntity<String> addUserToHouse(@RequestBody User userDetails, @RequestParam Long houseId) {
        try {
            houseService.addUserToHouse(userDetails, houseId);
            return ResponseEntity.ok("User successfully added");
        }
        catch(UserAlreadyExistsException ex) {
            return ResponseEntity.ok("User already exists in house");
        }
        catch(UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        catch(NoSuchElementException ex)
        {
            return ResponseEntity.notFound().build();
        }

    }
}
