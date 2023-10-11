package com.plantcare.serverapplication.hardwaremanagement.sensor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorTypeRepository extends JpaRepository<SensorType, Integer> {

    Optional<SensorType> getSensorTypeByName(String name);
}
