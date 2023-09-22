package com.plantcare.serverapplication.farmmanagement.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<List<Task>> findAllByContainerId(int containerId);
}
