package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.hardwaremanagement.sensor.Sensor;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FarmServiceImpl(FarmRepository farmRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public FarmDto getFarmById(int farmId) {

        // fix catch exceptions
        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        Optional<Sensor> temperatureAndHumiditySensor = Optional.of(farm.getRoomTemperatureAndHumidity());

        FarmDto farmDto = FarmDto
                .builder()
                .id(farm.getId())
                .name(farm.getName())
                .location(farm.getLocation())
                .roomTemperatureAndHumiditySensorId(temperatureAndHumiditySensor.get().getId())
                .build();

        return farmDto;
    }

    @Override
    public FarmDto addFarm(FarmDto farmDto) {

        User currentUser = this.getCurrentUser();

        Farm farm = Farm
                .builder()
                .name(farmDto.getName())
                .location(farmDto.getLocation())
                .build();

        currentUser.getFarms().add(farm);
        farm.getUsers().add(currentUser);

        Farm savedFarm = this.farmRepository.save(farm);

        return this.mapToDto(savedFarm);
    }

    @Override
    public List<FarmDto> getAllFarms() {

        User currentUser = this.getCurrentUser();

        return currentUser.getFarms().stream().map((farm -> this.mapToDto(farm))).collect(Collectors.toList());
    }

    @Override
    public void deleteFarmById(int farmId) {
        
        User currentUser = this.getCurrentUser();
        
        Farm farm = this.farmRepository.findById(farmId).orElseThrow();
        
        if (!currentUser.getFarms().contains(farm)) {
            // fix - throw some exception
        } else {
            currentUser.getFarms().remove(farm);
            farm.getUsers().remove(currentUser);

            this.farmRepository.delete(farm);
        }
    }

    @Override
    public FarmDto updateFarm(FarmDto farmDto, int farmId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        if (!currentUser.getFarms().contains(farm)) {
            // fix - throw some exception
        } else {
            farm.setName(farmDto.getName());
            farm.setLocation(farmDto.getLocation());
        }

        Farm savedFarm = this.farmRepository.save(farm);

        return this.mapToDto(savedFarm);
    }

    @Override
    public List<UserDto> getAllFarmersByFarmId(int farmId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        if (!currentUser.getFarms().contains(farm)) {
            // fix - throw some exception
        }

        List<User> farmers = farm.getUsers()
                .stream()
                .filter(user -> user.getRole().getRoleName().equals(RoleEnum.ROLE_FARMER))
                .collect(Collectors.toList());

        return farmers.stream().map(farmer -> {
           return UserDto
                   .builder()
                   .id(farmer.getId())
                   .email(farmer.getEmail())
                   .firstName(farmer.getFirstName())
                   .lastName(farmer.getLastName())
                   .build();
        }).collect(Collectors.toList());
    }

    @Override
    public void removeFarmerByFarm(int farmId, int farmerId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId).orElseThrow();

        if (!currentUser.getFarms().contains(farm)) {
            // fix - throw some exception
        }

        User farmer = this.userRepository.findById(farmerId).orElseThrow();

        farm.getUsers().remove(farmer);
        farmer.getFarms().remove(farm);

        this.farmRepository.save(farm);
        this.userRepository.save(farmer);
    }


    private FarmDto mapToDto(Farm farm) {
        return this.modelMapper.map(farm, FarmDto.class);
    }

    private Farm mapToEntity(FarmDto farmDto) {
        return this.modelMapper.map(farmDto, Farm.class);
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
