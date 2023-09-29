package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.hardwaremanagement.sensor.Sensor;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import com.plantcare.serverapplication.usermanagement.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {

    private final UserService userService;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FarmServiceImpl(UserService userService, FarmRepository farmRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FarmDto getFarmById(int farmId) {

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

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
                .owner(currentUser)
                .location(farmDto.getLocation())
                .users(new ArrayList<>())
                .build();

        farm.getUsers().add(currentUser);

        Farm savedFarm = this.farmRepository.save(farm);

        return this.convertToDto(savedFarm);
    }

    @Override
    public List<FarmDto> getAllFarms() {

        User currentUser = this.getCurrentUser();

        return currentUser.getFarms().stream().map((farm -> this.convertToDto(farm))).collect(Collectors.toList());
    }

    @Override
    public void deleteFarmById(int farmId) {
        
        User currentUser = this.getCurrentUser();
        
        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        currentUser.getFarms().remove(farm);
        farm.getUsers().forEach((user -> user.getFarms().remove(farm)));

        this.farmRepository.delete(farm);
    }

    @Override
    public FarmDto updateFarm(FarmDto farmDto, int farmId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        farm.setName(farmDto.getName());
        farm.setLocation(farmDto.getLocation());


        Farm updatedFarm = this.farmRepository.save(farm);

        return this.convertToDto(updatedFarm);
    }

    @Override
    public List<UserDto> getAllFarmersByFarmId(int farmId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
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
                   .isAccountNonLocked(farmer.isAccountNonLocked())
                   .firstName(farmer.getFirstName())
                   .lastName(farmer.getLastName())
                   .build();
        }).collect(Collectors.toList());
    }

    @Override
    public void removeFarmerByFarm(int farmId, int farmerId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        User farmer = this.userRepository.findById(farmerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", farmerId));

        farm.getUsers().remove(farmer);
        farmer.getFarms().remove(farm);

        this.farmRepository.save(farm);
        this.userRepository.save(farmer);
    }

    @Override
    public void changeFarmOwnership(int farmId, int newOwnerId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        User newOwner = this.userRepository.findById(newOwnerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", newOwnerId));

        farm.setOwner(newOwner);
        this.farmRepository.save(farm);
    }


    private boolean isValidFarmAccess(User currentUser, Farm farm) {
        return currentUser.getFarms().contains(farm);
    }

    private FarmDto convertToDto(Farm farm) {
        return FarmDto
                .builder()
                .id(farm.getId())
                .name(farm.getName())
                .location(farm.getLocation())
                .owner(this.userService.convertToDto(farm.getOwner()))
                .build();
    }

    private Farm mapToEntity(FarmDto farmDto) {
        return this.modelMapper.map(farmDto, Farm.class);
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
