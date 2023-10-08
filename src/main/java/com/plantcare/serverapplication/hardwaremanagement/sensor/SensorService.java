package com.plantcare.serverapplication.hardwaremanagement.sensor;

import java.util.List;

public interface SensorService {

    SensorDto convertToDto(Sensor sensor);

    List<SensorDto> getAllSensorsByFarmId(int farmId);

    SensorDto getSensorById(int farmId, int sensorId);
}
