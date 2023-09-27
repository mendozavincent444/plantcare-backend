package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.farmmanagement.plant.PlantDto;
import com.plantcare.serverapplication.farmmanagement.task.Task;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoardDto;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContainerDto {

    private int id;

    @Size(max = 20)
    private String name;

    private ArduinoBoardDto arduinoBoardDto;

    private PlantDto plantDto;

    private int farmId;
}
