package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.farmmanagement.container.Container;
import com.plantcare.serverapplication.farmmanagement.task.Task;
import com.plantcare.serverapplication.hardwaremanagement.pump.Pump;
import com.plantcare.serverapplication.hardwaremanagement.sensor.Sensor;
import com.plantcare.serverapplication.shared.UserDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarmDto {

    private int id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private String location;

    private UserDto owner;

    private Integer roomTemperatureAndHumiditySensorId;
}
