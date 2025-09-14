package com.bartoszswiech.home_inventory_api.beans;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Set;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    private String firstName;
    private String lastName;

    private String email;
    private String username;
    private String password;

    private boolean admin = false;


    @ManyToMany
    @JoinTable(
            name = "users_in_house",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id"))
    @JsonIgnore
    private Set<House> houses;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public void addHouse(House newHouse) {
        if(newHouse == null) throw new IllegalArgumentException("newHouse argument can not be null");
        this.houses.add(newHouse);
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return String.format("[id: %d, firstName: %s, lastName: %s, email: %s, username: %s, admin: %b]", getId(), getFirstName(), getLastName(), getEmail(), getUsername(), isAdmin());
    }
}
