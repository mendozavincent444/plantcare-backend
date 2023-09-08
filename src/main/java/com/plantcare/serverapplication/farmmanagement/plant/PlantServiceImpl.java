package com.plantcare.serverapplication.farmmanagement.plant;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;
    private final ModelMapper modelMapper;

    public PlantServiceImpl(PlantRepository plantRepository,
                            ModelMapper modelMapper,
                            FarmRepository farmRepository) {
        this.plantRepository = plantRepository;
        this.farmRepository = farmRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PlantDto getPlantById(int plantId) {

        // fix handle exceptions
        Plant plant = this.plantRepository.findById(plantId).orElseThrow();

        return this.mapToDto(plant);
    }

    @Override
    public List<PlantDto> getAllPlantsByFarmId(int farmId) {
        List<Plant> plants = this.plantRepository.findAllByFarmId(farmId);

        return plants.stream().map(plant -> this.mapToDto(plant)).collect(Collectors.toList());
    }

    @Override
    public void deletePlantById(int farmId, int plantId) {

        Plant plant = this.plantRepository.findById(plantId).orElseThrow();
        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        farm.getPlants().remove(plant);

        this.plantRepository.delete(plant);
    }


    private PlantDto mapToDto(Plant plant) {
        return this.modelMapper.map(plant, PlantDto.class);
    }

    private Plant mapToEntity(PlantDto plantDto) {
        return this.modelMapper.map(plantDto, Plant.class);
    }
}
