package com.plantcare.serverapplication.farmmanagement.container;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContainerRepository extends JpaRepository<Container, Integer> {
    List<Container> findAllByFarmId(int farmId);
}
