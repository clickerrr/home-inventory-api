package com.bartoszswiech.home_inventory_api.beans;

import com.bartoszswiech.home_inventory_api.exceptions.UserAlreadyExistsException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;
    private String title;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "house_rooms")
    private Set<Room> rooms = new HashSet<Room>();


    @ManyToMany(mappedBy = "houses")
    private Set<User> users;

    @OneToMany(mappedBy = "associatedHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "house_inventory")
    private Set<Inventory> inventories;

    public Set<Room> getRooms() {
        return Set.copyOf(rooms);
    }

    public void setRooms(Set<Room> updatedRooms) {
        this.rooms = updatedRooms;
    }

    // TODO: prevent direct modification of rooms object
//    public int updateRooms(Set<Room> updatedRooms) {
//        for (Room updatedRoom : updatedRooms) {
//
//        }
//    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<User> getUsers() {
        return Set.copyOf(users);
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", title='" + title +
                '}';
    }

    public void addUserToHouse(User foundUser) throws UserAlreadyExistsException {
        if (foundUser == null) {
            return;
        }

        if (users.contains(foundUser)) {
            throw new UserAlreadyExistsException();
        }

        System.out.println("Adding user to house " + foundUser.getUsername());
        users.add(foundUser);

    }

    public Set<Inventory> getInventories() {
        return Set.copyOf(this.inventories);
    }

    public void addAnInventory(Inventory newInventory) {
        if (newInventory == null) {
            throw new IllegalArgumentException();
        }
        this.inventories.add(newInventory);
    }

    public void updateInventory(Inventory inventoryToUpdate) {
        if (inventoryToUpdate == null) {
            throw new IllegalArgumentException();
        }

    }
}