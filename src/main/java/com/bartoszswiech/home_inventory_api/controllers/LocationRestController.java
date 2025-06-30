package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.Location;
import com.bartoszswiech.home_inventory_api.beans.LoggedItem;
import com.bartoszswiech.home_inventory_api.interfaces.LoggedItemWithProduct;
import com.bartoszswiech.home_inventory_api.services.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/locations")
public class LocationRestController {

    private final LocationService locationService;

    public LocationRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{id}/loggedItems")
    public List<LoggedItemWithProduct> getAllLoggedItems(@PathVariable Long id) {
        return locationService.getLocationLoggedItems(id);
    }

    @PostMapping
    public Location createLocation(@RequestBody Location newLocation, @RequestParam Long roomId) {
        return locationService.createLocation(newLocation, roomId);
    }

    @GetMapping("/{id}")
    public Location getLocation(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @PutMapping("/{id}")
    public Location updateLocation(@RequestBody Location updatedLocation, @PathVariable Long id) {
        return locationService.update(id, updatedLocation);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteById(id);
    }
}
