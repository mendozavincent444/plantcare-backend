package com.plantcare.serverapplication.hardwaremanagement.sensor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<List<Sensor>> getAllSensorsByFarmId(int farmId);
}
