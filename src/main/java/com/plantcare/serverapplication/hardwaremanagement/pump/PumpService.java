package com.plantcare.serverapplication.hardwaremanagement.pump;

import java.util.List;

public interface PumpService {
    List<PumpDto> getAllPumpsByFarmId(int farmId);
}
