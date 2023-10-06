package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ArduinoBoardRepository extends JpaRepository<ArduinoBoard, Integer> {

    Optional<List<ArduinoBoard>> findAllByFarmId(int farmId);
}
