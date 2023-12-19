package com.plantcare.serverapplication.shared;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArduinoBoardData {
    private double currentHumidity;
    private double currentTds;
    private double currentTemperature;
    private double currentWaterLevel;
    private double currentpH;
    private double maxTDS;
    private double maxpH;
    private double minTDS;
    private double minpH;
}
