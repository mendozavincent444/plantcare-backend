package com.plantcare.serverapplication.farmmanagement.farm;

public interface FarmService {

    FarmDto getFarmById(int farmId);

    FarmDto addFarm(FarmDto farmDto);
}
