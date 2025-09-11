package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.beans.Location;
import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.exceptions.ItemsExistException;
import com.bartoszswiech.home_inventory_api.repositories.RoomRepository;
import com.bartoszswiech.home_inventory_api.repositories.HouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public List<Room> findAll(Long houseId) {
        House associatedHouse = houseRepository.findById(houseId).orElseThrow();

        return associatedHouse.getRooms().stream().toList();
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
                    return roomRepository.save(room);
                })
                .orElseGet(() -> {
                    return roomRepository.save(updatedRoom);
                });
    }

    public void deleteById(Long id) {

        Room returnedRoom = roomRepository.findById(id).orElseThrow( () -> new EntryNotFoundException(id.toString()));

        Set<Location> locations = returnedRoom.getLocations();

        locations.forEach( location -> {
            if(!location.getLoggedItems().isEmpty()) {
                throw new ItemsExistException();
            }
        });

        roomRepository.deleteById(id);
    }
}
