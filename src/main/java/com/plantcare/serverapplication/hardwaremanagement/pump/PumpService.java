package com.plantcare.serverapplication.hardwaremanagement.pump;

import java.util.List;

public interface PumpService {
    List<PumpDto> getAllPumpsByFarmId(int farmId);

    PumpDto getPumpById(int farmId, int pumpId);
    void deletePumpById(int farmId, int pumpId);
    PumpDto addPump(PumpDto pumpDto, int farmId);
}
