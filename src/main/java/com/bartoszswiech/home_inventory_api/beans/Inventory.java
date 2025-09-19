package com.bartoszswiech.home_inventory_api.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @Column(name="inventory_title")
    private String title;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "inventory_loggeditems")
    private Set<LoggedItem> loggedItems = new HashSet<LoggedItem>();

    @ManyToOne
    @JoinColumn(name = "house_id")
    @JsonBackReference(value = "house_inventory")
    private House associatedHouse;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<LoggedItem> getLoggedItems() {
        return Set.copyOf(loggedItems);
    }

    public void addLoggedItem(LoggedItem newLoggedItem) {
        if(newLoggedItem == null) {
            throw new IllegalArgumentException();
        }
        this.loggedItems.add(newLoggedItem);
    }

    public void setAssociatedHouse(House house) {
        if (house == null) {
            throw new IllegalArgumentException();
        }
        this.associatedHouse = house;
    }

    public void removeAssociatedHouse() {
        this.associatedHouse = null;
    }

    public House getAssociatedHouse() {
        return this.associatedHouse;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id + ", " +
                "title=" + title +
                '}';
    }


}
