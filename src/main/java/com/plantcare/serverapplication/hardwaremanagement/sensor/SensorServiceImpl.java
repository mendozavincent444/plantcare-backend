package com.plantcare.serverapplication.hardwaremanagement.sensor;

import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {


    @Override
    public SensorDto convertToDto(Sensor sensor) {
        if (sensor == null) {
            return null;
        }

        return SensorDto
                .builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .type(sensor.getType())
                .status(sensor.getStatus())
                .build();
    }
}
