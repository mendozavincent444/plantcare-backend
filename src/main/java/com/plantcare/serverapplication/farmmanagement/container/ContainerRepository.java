package com.plantcare.serverapplication.farmmanagement.container;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContainerRepository extends JpaRepository<Container, Integer> {
    Optional<List<Container>> findAllByFarmId(int farmId);
}
