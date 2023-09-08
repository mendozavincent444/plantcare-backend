package com.plantcare.serverapplication.farmmanagement.plant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Integer> {

    List<Plant> findAllByFarmId(int farmId);
}
