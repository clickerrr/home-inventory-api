package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.services.HouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public House createHouse(@RequestBody House newHouse) {
        return houseService.createHouse(newHouse);
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
}
