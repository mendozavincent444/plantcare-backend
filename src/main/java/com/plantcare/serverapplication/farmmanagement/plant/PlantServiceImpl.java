package com.plantcare.serverapplication.farmmanagement.plant;

import org.modelmapper.ModelMapper;

public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final ModelMapper modelMapper;

    public PlantServiceImpl(PlantRepository plantRepository, ModelMapper modelMapper) {
        this.plantRepository = plantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PlantDto getPlantById(int plantId) {

        // fix handle exceptions
        Plant plant = this.plantRepository.findById(plantId).orElseThrow();

        return this.mapToDto(plant);
    }


    private PlantDto mapToDto(Plant plant) {
        return this.modelMapper.map(plant, PlantDto.class);
    }

    private Plant mapToEntity(PlantDto plantDto) {
        return this.modelMapper.map(plantDto, Plant.class);
    }
}
