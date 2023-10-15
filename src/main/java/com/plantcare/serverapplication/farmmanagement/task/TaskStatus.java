package com.plantcare.serverapplication.farmmanagement.task;

import lombok.Getter;

@Getter
public enum TaskStatus {
    GROWING("Growing"),
    Harvesting("Harvesting");

    private String name;

    TaskStatus(String name) {
        this.name = name;
    }
}
