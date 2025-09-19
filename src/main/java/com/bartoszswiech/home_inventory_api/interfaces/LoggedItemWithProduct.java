package com.bartoszswiech.home_inventory_api.interfaces;

import com.bartoszswiech.home_inventory_api.beans.Location;
import com.bartoszswiech.home_inventory_api.beans.LoggedItem;
import com.bartoszswiech.home_inventory_api.beans.Product;

import java.time.LocalDate;

public interface LoggedItemWithProduct {
    Long getId();
    LocalDate getDateLogged();
    LocalDate getExpirationDate();
    LocalDate getConsumeByDate();
    ProductView getProduct();
    LocationView getLocation();
}
