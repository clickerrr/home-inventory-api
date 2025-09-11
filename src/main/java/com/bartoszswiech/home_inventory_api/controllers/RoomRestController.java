package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.Location;
import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.services.RoomService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rooms")
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getRoomsByHouse(@RequestParam Long houseId) {
        try {
            return ResponseEntity.ok( roomService.findAll(houseId));
        }
        catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public Room createRoom(@RequestBody Room newRoom, @RequestParam Long houseId) {
        return roomService.createRoom(newRoom, houseId);
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping("/{id}/locations")
    public Set<Location> getRoomLocations(@PathVariable Long id) {
        return roomService.getAllLocations(id);
    }


    @PutMapping("/{id}")
    public Room updateRoom(@RequestBody Room updatedRoom, @PathVariable Long id) {
        return roomService.update(id, updatedRoom);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);

    }
}
