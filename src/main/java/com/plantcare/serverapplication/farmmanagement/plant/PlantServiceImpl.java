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
    public PlantDto addPlant(PlantDto plantDto, int farmId) {

        // check if user has access to farm
        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        Plant plant = Plant
                .builder()
                .name(plantDto.getName())
                .maximumEc(plantDto.getMaximumEc())
                .maximumPh(plantDto.getMaximumPh())
                .minimumEc(plantDto.getMinimumEc())
                .maximumPh(plantDto.getMaximumPh())
                .daysToMaturity(plantDto.getDaysToMaturity())
                .farm(farm)
                .build();

        farm.getPlants().add(plant);

        Plant savedPlant = this.plantRepository.saveAndFlush(plant);

        return this.convertToDto(savedPlant);
    }

    @Override
    public PlantDto getPlantById(int farmId, int plantId) {

        // check if plant id is from a farm user has access to

        // fix handle exceptions
        Plant plant = this.plantRepository.findById(plantId).orElseThrow();

        return this.mapToDto(plant);
    }

    @Override
    public List<PlantDto> getAllPlantsByFarmId(int farmId) {
        List<Plant> plants = this.plantRepository.findAllByFarmId(farmId).orElseThrow();

        return plants.stream().map(plant -> this.mapToDto(plant)).collect(Collectors.toList());
    }

    @Override
    public void deletePlantById(int farmId, int plantId) {

        Plant plant = this.plantRepository.findById(plantId).orElseThrow();
        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        farm.getPlants().remove(plant);

        this.plantRepository.delete(plant);
    }

    @Override
    public PlantDto updatePlant(PlantDto plantDto, int farmId, int plantId) {

        // check if user is authorized to update the plant
        //Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        Plant plant = this.plantRepository.findById(plantId).orElseThrow();

        plant.setName(plantDto.getName());
        plant.setMaximumEc(plantDto.getMaximumEc());
        plant.setMaximumPh(plantDto.getMaximumPh());
        plant.setMinimumEc(plantDto.getMinimumEc());
        plant.setMinimumPh(plantDto.getMinimumPh());
        plant.setDaysToMaturity(plantDto.getDaysToMaturity());

        Plant updatedPlant = this.plantRepository.save(plant);

        return this.convertToDto(updatedPlant);
    }


    private PlantDto mapToDto(Plant plant) {
        return this.modelMapper.map(plant, PlantDto.class);
    }

    private Plant mapToEntity(PlantDto plantDto) {
        return this.modelMapper.map(plantDto, Plant.class);
    }

    private PlantDto convertToDto(Plant plant) {
        return PlantDto
                .builder()
                .id(plant.getId())
                .name(plant.getName())
                .maximumEc(plant.getMaximumEc())
                .maximumPh(plant.getMaximumPh())
                .minimumEc(plant.getMinimumEc())
                .maximumPh(plant.getMaximumPh())
                .daysToMaturity(plant.getDaysToMaturity())
                .farmId(plant.getFarm().getId())
                .build();
    }
}
