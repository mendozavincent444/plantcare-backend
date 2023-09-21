package com.plantcare.serverapplication.farmmanagement.plant;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PlantServiceImpl(
            PlantRepository plantRepository,
            FarmRepository farmRepository,
            UserRepository userRepository,
            ModelMapper modelMapper
    ) {
        this.plantRepository = plantRepository;
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PlantDto addPlant(PlantDto plantDto, int farmId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        if (!isValidFarmAccess(currentUser, farm)) {
            // to fix - throw exception
        }

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

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        if (!isValidFarmAccess(currentUser, farm)) {
            // to fix - throw exception
        }

        // fix handle exceptions
        Plant plant = this.plantRepository.findById(plantId).orElseThrow();

        return this.mapToDto(plant);
    }

    @Override
    public List<PlantDto> getAllPlantsByFarmId(int farmId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        if (!isValidFarmAccess(currentUser, farm)) {
            // to fix - throw exception
        }

        List<Plant> plants = this.plantRepository.findAllByFarmId(farmId).orElseThrow();

        return plants.stream().map(plant -> this.mapToDto(plant)).collect(Collectors.toList());
    }

    @Override
    public void deletePlantById(int farmId, int plantId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        if (!isValidFarmAccess(currentUser, farm)) {
            // to fix - throw exception
        }

        Plant plant = this.plantRepository.findById(plantId).orElseThrow();

        farm.getPlants().remove(plant);

        this.plantRepository.delete(plant);
    }

    @Override
    public PlantDto updatePlant(PlantDto plantDto, int farmId, int plantId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        if (!isValidFarmAccess(currentUser, farm)) {
            // to fix - throw exception
        }

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

    private boolean isValidFarmAccess(User currentUser, Farm farm) {
        return currentUser.getFarms().contains(farm);
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

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
