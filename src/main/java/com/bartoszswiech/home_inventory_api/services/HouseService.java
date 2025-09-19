package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.beans.Inventory;
import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.exceptions.UserAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.UserNotFoundException;
import com.bartoszswiech.home_inventory_api.interfaces.UserView;
import com.bartoszswiech.home_inventory_api.repositories.HouseRepository;
import com.bartoszswiech.home_inventory_api.repositories.InventoryRepository;
import com.bartoszswiech.home_inventory_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class HouseService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public House createHouse(House newHouse) {
        if (newHouse.getId() != null && houseRepository.existsById(newHouse.getId())) {
            throw new EntryAlreadyExistsException(newHouse.getId().toString());
        }
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User associatedUser = userRepository.findByUsername(currentUsername);
        newHouse.addUserToHouse(associatedUser);
        associatedUser.addHouse(newHouse);

        return houseRepository.save(newHouse);
    }


    public List<House> findAll() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<House> foundHouses = userRepository.findByUsername(currentUsername).getHouses();
        System.out.println("!!!!! found houses " + foundHouses.toString());
        List<House> returnList = new ArrayList<House>();

        for(House foundHouse: foundHouses) {
            System.out.println(foundHouse);
            returnList.add(foundHouse);
        }
        return returnList;
    }

    public House findById(Long id) throws UserNotFoundException {

        House foundHouse = houseRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));
        checkIfCurrentUserInHouse(foundHouse);

        return foundHouse;
    }

    public void checkIfCurrentUserInHouse(House houseToCheck) throws UserNotFoundException {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User associatedUser = userRepository.findByUsername(currentUsername);
        if(associatedUser == null) {
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public House update(Long id, House updatedHouse) {

        House foundHouse = houseRepository.findById(id).orElseThrow();

        checkIfCurrentUserInHouse(foundHouse);

        Set<Room> newRooms = updatedHouse.getRooms();
        foundHouse.setRooms(newRooms);
        foundHouse.setTitle(updatedHouse.getTitle());

        houseRepository.save(foundHouse);

        return foundHouse;
    }

    public void deleteById(Long id) {
        houseRepository.deleteById(id);
    }

    public List<UserView> getUserDetails(Long houseId) throws UserNotFoundException {
        House foundHouse = houseRepository.findById(houseId).orElseThrow();

        System.out.println(foundHouse.getId());
        System.out.println(foundHouse.getUsers().toString());

        checkIfCurrentUserInHouse(foundHouse);

        return foundHouse.getUsers().stream().map(user -> userRepository.findUserViewById(user.getId())).toList();

    }

    @Transactional
    public void addUserToHouse(User userDetails, Long houseId) throws UserAlreadyExistsException, UserNotFoundException {
        House foundHouse = houseRepository.findById(houseId).orElseThrow();

        checkIfCurrentUserInHouse(foundHouse);

        String username = userDetails.getUsername();
        String email = userDetails.getEmail();
        User foundUser = null;
        if(username != null) {
            foundUser = userRepository.findByUsername(username);
        }
        if(foundUser == null && email != null) {
            foundUser = userRepository.findByEmail(email);
        }

        if(foundUser == null) {
            throw new UserNotFoundException();
        }

        foundHouse.addUserToHouse(foundUser);
        foundUser.addHouse(foundHouse);
        userRepository.save(foundUser);
        houseRepository.save(foundHouse);

    }

    @Transactional
    public void addInventoryToHouse(Long houseId, Long inventoryId) throws UserNotFoundException, NoSuchElementException {
        Inventory foundInventory = inventoryRepository.findById(inventoryId).orElseThrow();
        House foundHouse = findById(houseId);
        foundInventory.setAssociatedHouse(foundHouse);
        inventoryRepository.save(foundInventory);

    }
}
