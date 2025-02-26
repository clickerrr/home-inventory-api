package com.bartoszswiech.home_inventory_api.beans;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Product {

    @Id
    private String upca;
    private String title;
    private CONTAINER_TYPE containerType;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value= "product_nutritional")
    private NutritionalInformation nutritionalInformation;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "product_loggeditems")
    private Set<LoggedItem> loggedItems = new HashSet<LoggedItem>();

    protected Product() {}

    public Product(String upca, String title, CONTAINER_TYPE containerType, NutritionalInformation nutritionalInformation) {
        this.upca = upca;
        this.title = title;
        this.containerType = containerType;
        this.nutritionalInformation = nutritionalInformation;
    }

    public String getUpca() {
        return upca;
    }

    public void setUpca(String upca) {
        this.upca = upca;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CONTAINER_TYPE getContainerType() {
        return containerType;
    }

    public void setContainerType(CONTAINER_TYPE containerType) {
        this.containerType = containerType;
    }

    public NutritionalInformation getNutritionalInformation() {
        return nutritionalInformation;
    }

    public void setNutritionalInformation(NutritionalInformation nutritionalInformation) {
        this.nutritionalInformation = nutritionalInformation;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Product productObject))
            return false;

        return Objects.equals(productObject.getUpca(), this.upca);

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return String.format("Product(upca: %s, title: %s, containerType: %s, %s)", this.upca, this.title, this.containerType, this.nutritionalInformation);
    }
}
