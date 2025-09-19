package com.bartoszswiech.home_inventory_api.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class LoggedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate dateLogged;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate expirationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate consumeByDate;

    @ManyToOne
    @JoinColumn(name = "upca", referencedColumnName="upca")
    @JsonBackReference(value = "product_loggeditems")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonBackReference(value = "location_loggeditems")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    @JsonBackReference(value = "inventory_loggeditems")
    private Inventory inventory;


    public LoggedItem() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(LocalDate dateLogged) {
        this.dateLogged = dateLogged;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getConsumeByDate() {
        return consumeByDate;
    }

    public void setConsumeByDate(LocalDate consumeByDate) {
        this.consumeByDate = consumeByDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(LocatioqSn location) {
        this.location = location;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "LoggedItem{" +
                "id=" + id +
                ", dateLogged=" + dateLogged +
                ", expirationDate=" + expirationDate +
                ", consumeByDate=" + consumeByDate +
                ", product=" + product +
                ", location=" + location +
                ", inventory=" + inventory.getId() +
                '}';
    }
}
