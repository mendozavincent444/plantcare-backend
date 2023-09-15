package com.plantcare.serverapplication.farmmanagement.farm;

import java.util.List;

public interface FarmService {

    FarmDto getFarmById(int farmId);

    FarmDto addFarm(FarmDto farmDto);

    List<FarmDto> getAllFarms();
}
