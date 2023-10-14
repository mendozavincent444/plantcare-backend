package com.plantcare.serverapplication.farmmanagement.plant;

import java.util.List;

public interface PlantService {

    PlantDto addPlant(PlantDto plantDto, int farmId);

    PlantDto getPlantById(int farmId, int plantId);

    List<PlantDto> getAllPlantsByFarmId(int farmId);

    void deletePlantById(int farmId, int plantId);

    PlantDto updatePlant(PlantDto plantDto, int farmId, int plantId);
    PlantDto convertToDto(Plant plant);
}
