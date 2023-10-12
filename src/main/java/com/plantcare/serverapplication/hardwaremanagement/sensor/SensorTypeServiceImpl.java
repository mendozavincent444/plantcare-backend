package com.plantcare.serverapplication.hardwaremanagement.sensor;

import java.util.List;

public class SensorTypeServiceImpl implements SensorTypeService {

    private final SensorTypeRepository sensorTypeRepository;

    public SensorTypeServiceImpl(SensorTypeRepository sensorTypeRepository) {
        this.sensorTypeRepository = sensorTypeRepository;
    }

    @Override
    public List<SensorType> getAllSensorTypes() {
        return this.sensorTypeRepository.findAll();
    }
}
