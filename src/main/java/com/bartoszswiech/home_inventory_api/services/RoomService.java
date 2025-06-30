package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.beans.Location;
import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.RoomRepository;
import com.bartoszswiech.home_inventory_api.repositories.HouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HouseRepository houseRepository;

    public RoomService(RoomRepository roomRepository, HouseRepository houseRepository) {
        this.roomRepository = roomRepository;
        this.houseRepository = houseRepository;
    }

    @Transactional
    public Room createRoom(Room newRoom, Long houseId) {
        if (newRoom.getId() != 0 && roomRepository.existsById(newRoom.getId())) {
            throw new EntryAlreadyExistsException(String.valueOf(newRoom.getId()));
        }

        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new EntryNotFoundException(String.valueOf(houseId)));

        newRoom.setHouse(house);
        return roomRepository.save(newRoom);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(String.valueOf(id)));
    }

    public Set<Location> getAllLocations(Long roomId) {
        return findById(roomId).getLocations();
    }

    @Transactional
    public Room update(Long id, Room updatedRoom) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setTitle(updatedRoom.getTitle());
                    Set<Location> locations = updatedRoom.getLocations();
                    room.setLocations(locations);
                    room.setHouse(updatedRoom.getHouse());
                    return roomRepository.save(room);
                })
                .orElseGet(() -> {
                    return roomRepository.save(updatedRoom);
                });
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
}
