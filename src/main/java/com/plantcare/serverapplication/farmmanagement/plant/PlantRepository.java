package com.plantcare.serverapplication.farmmanagement.plant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Integer> {

    Optional<List<Plant>> findAllByFarmId(int farmId);
}
