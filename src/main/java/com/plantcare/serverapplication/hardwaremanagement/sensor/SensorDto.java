package com.plantcare.serverapplication.hardwaremanagement.sensor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorDto {
    private int id;
    private String name;
    private String status;
    private String sensorTypeName;
}
