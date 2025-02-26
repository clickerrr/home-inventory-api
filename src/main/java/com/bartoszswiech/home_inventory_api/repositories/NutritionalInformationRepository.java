package com.bartoszswiech.home_inventory_api.repositories;

import com.bartoszswiech.home_inventory_api.beans.NutritionalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionalInformationRepository extends JpaRepository<NutritionalInformation, Long> {
}
