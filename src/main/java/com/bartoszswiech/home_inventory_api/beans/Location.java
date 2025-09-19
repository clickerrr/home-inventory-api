package com.bartoszswiech.home_inventory_api.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference(value = "room_locations")
    private Room room;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "location_loggeditems")
    private Set<LoggedItem> loggedItems = new HashSet<LoggedItem>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Set<LoggedItem> getLoggedItems() {
        return Set.copyOf(loggedItems);
    }

    public void addLoggedItem(LoggedItem newLoggedItem) {
        if(newLoggedItem == null) {
            throw new IllegalArgumentException();
        }
        loggedItems.add(newLoggedItem);
    }



    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", room=" + room +
                '}';
    }
}
