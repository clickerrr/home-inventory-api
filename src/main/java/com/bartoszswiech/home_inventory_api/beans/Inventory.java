package com.bartoszswiech.home_inventory_api.beans;

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

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "inventory_loggeditems")
    private Set<LoggedItem> loggedItems = new HashSet<LoggedItem>();

    public Long getId() {
        return id;
    }

    public Set<LoggedItem> getLoggedItems() {
        return loggedItems;
    }

    public void setLoggedItems(Set<LoggedItem> loggedItems) {
        this.loggedItems = loggedItems;
    }
}
