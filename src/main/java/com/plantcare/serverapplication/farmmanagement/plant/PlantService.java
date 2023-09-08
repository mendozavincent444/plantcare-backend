package com.plantcare.serverapplication.farmmanagement.plant;

import java.util.List;

public interface PlantService {

    PlantDto getPlantById(int plantId);

    List<PlantDto> getAllPlantsByFarmId(int farmId);

    void deletePlantById(int farmId, int plantId);
}
