package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.exception.SubscriptionNotFoundException;
import com.plantcare.serverapplication.farmmanagement.container.Container;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoard;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoardDto;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoardRepository;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoardService;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.DeviceStatus;
import com.plantcare.serverapplication.shared.MessageResponseDto;
import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.usermanagement.role.RoleEnum;
import com.plantcare.serverapplication.usermanagement.subscription.Subscription;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import com.plantcare.serverapplication.usermanagement.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {
    private final UserService userService;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final ArduinoBoardRepository arduinoBoardRepository;
    private final ArduinoBoardService arduinoBoardService;

    public FarmServiceImpl(
            UserService userService,
            FarmRepository farmRepository,
            UserRepository userRepository,
            ArduinoBoardRepository arduinoBoardRepository,
            ArduinoBoardService arduinoBoardService) {
        this.userService = userService;
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.arduinoBoardRepository = arduinoBoardRepository;
        this.arduinoBoardService = arduinoBoardService;
    }

    @Override
    public FarmDto setMainArduinoBoard(int farmId, int arduinoBoardId) {

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(arduinoBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("Arduino board", "id", arduinoBoardId));

        farm.setMainArduinoBoard(arduinoBoard);
        arduinoBoard.setStatus(DeviceStatus.ACTIVE);

        Farm savedFarm = this.farmRepository.save(farm);

        return this.convertToDto(savedFarm);
    }

    @Override
    public FarmDto removeMainArduinoBoard(int farmId) {
        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        ArduinoBoard arduinoBoard = farm.getMainArduinoBoard();

        arduinoBoard.setStatus(DeviceStatus.INACTIVE);

        farm.setMainArduinoBoard(null);

        Farm savedFarm = this.farmRepository.save(farm);

        return this.convertToDto(savedFarm);
    }

    @Override
    public ArduinoBoardDto getMainArduinoBoardByFarmId(int farmId) {
        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        ArduinoBoard mainArduinoBoard = farm.getMainArduinoBoard();

        if (mainArduinoBoard == null) {
            throw new ResourceNotFoundException("Main Arduino Board", "farm", farmId);
        }

        return this.arduinoBoardService.convertToDto(mainArduinoBoard);
    }

    @Override
    public FarmDto getFarmById(int farmId) {

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        int mainArduinoBoardId = 0;

        if (farm.getMainArduinoBoard() != null) {
            mainArduinoBoardId = farm.getMainArduinoBoard().getId();
        }

        FarmDto farmDto = FarmDto
                .builder()
                .id(farm.getId())
                .name(farm.getName())
                .owner(this.userService.convertToDto(farm.getOwner()))
                .location(farm.getLocation())
                .mainArduinoBoardId(mainArduinoBoardId)
                .build();

        return farmDto;
    }

    @Override
    public FarmDto addFarm(FarmDto farmDto) {

        User currentUser = this.getCurrentUser();

        Subscription userSubscription = currentUser.getSubscription();

        if (!currentUser.getFarms().isEmpty() && userSubscription == null) {
            throw new SubscriptionNotFoundException("Premium subscription is required to add more farms.");
        }

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
    public FarmDto updateFarm(FarmDto farmDto, int farmId, int newOwnerId) {

        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        User newOwner = this.userRepository.findById(newOwnerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", newOwnerId));

        farm.setName(farmDto.getName());
        farm.setLocation(farmDto.getLocation());
        farm.setOwner(newOwner);

        Farm updatedFarm = this.farmRepository.save(farm);

        return this.convertToDto(updatedFarm);
    }

    @Override
    public List<UserDto> getAllUsersByFarmId(int farmId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        List<User> users = farm.getUsers();

        return users.stream().map(user -> this.userService.convertToDto(user)).toList();
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

        return farmers.stream().map(farmer -> this.userService.convertToDto(farmer)).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllAdminsByFarmId(int farmId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        List<User> farmers = farm.getUsers()
                .stream()
                .filter(user -> user.getRole().getRoleName().equals(RoleEnum.ROLE_ADMIN))
                .collect(Collectors.toList());

        return farmers.stream().map(farmer -> this.userService.convertToDto(farmer)).collect(Collectors.toList());
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

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
