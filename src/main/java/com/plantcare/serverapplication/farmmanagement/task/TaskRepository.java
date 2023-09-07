package com.plantcare.serverapplication.farmmanagement.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByFarmId(int farmId);
    List<Task> findAllByContainerId(int containerId);
}
