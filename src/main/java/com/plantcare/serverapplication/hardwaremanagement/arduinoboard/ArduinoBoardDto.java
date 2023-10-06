package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import com.plantcare.serverapplication.hardwaremanagement.arduinosensormapping.ArduinoSensorMapping;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
public class ArduinoBoardDto {

    private int id;
    @NotNull
    @Size(max = 20)
    private String name;
    @NotNull
    @Size(max = 20)
    private String status;

    private Set<ArduinoSensorMapping> sensorMappings;
}
