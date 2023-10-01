package com.plantcare.serverapplication.hardwaremanagement.sensor;

import lombok.Builder;

@Builder
public class SensorDto {
    private int id;
    private String name;
    private String type;
    private String status;
}
