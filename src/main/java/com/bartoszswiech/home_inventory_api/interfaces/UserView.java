package com.bartoszswiech.home_inventory_api.interfaces;

import com.bartoszswiech.home_inventory_api.beans.House;

import java.util.Set;

public interface UserView {
    Long getId();
    String getUsername();
    String getEmail();
    String getFirstName();
    String getLastName();
}
