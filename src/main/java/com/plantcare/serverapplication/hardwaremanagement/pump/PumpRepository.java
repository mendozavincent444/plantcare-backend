package com.plantcare.serverapplication.hardwaremanagement.pump;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PumpRepository extends JpaRepository<Pump, Integer> {
    Optional<List<Pump>> getAllPumpsByFarmId(int farmId);
}
