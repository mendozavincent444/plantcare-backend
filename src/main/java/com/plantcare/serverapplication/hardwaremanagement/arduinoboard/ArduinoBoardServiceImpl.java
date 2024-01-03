package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.exception.SubscriptionNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.firebase.FirebaseRestClient;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.DeviceStatus;
import com.plantcare.serverapplication.usermanagement.subscription.Subscription;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import com.plantcare.serverapplication.usermanagement.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class ArduinoBoardServiceImpl implements ArduinoBoardService {
    private final ArduinoBoardRepository arduinoBoardRepository;
    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final FirebaseRestClient firebaseRestClient;
    private final UserService userService;

    public ArduinoBoardServiceImpl(
            ArduinoBoardRepository arduinoBoardRepository,
            UserRepository userRepository,
            FarmRepository farmRepository,
            FirebaseRestClient firebaseRestClient,
            UserService userService
    ) {
        this.arduinoBoardRepository = arduinoBoardRepository;
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.firebaseRestClient = firebaseRestClient;
        this.userService = userService;
    }

    @Override
    public ArduinoBoardDto addArduinoBoard(ArduinoBoardDto arduinoBoardDto, int farmId) {
        User currentUser = this.userService.getCurrentUser();

        Subscription userSubscription = currentUser.getSubscription();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        if (!farm.getArduinoBoards().isEmpty() && userSubscription == null) {
            throw new SubscriptionNotFoundException("Premium subscription is required to add more Arduino Boards.");
        }

        ArduinoBoard arduinoBoard = ArduinoBoard
                .builder()
                .name(arduinoBoardDto.getName())
                .status(DeviceStatus.INACTIVE)
                .farm(farm)
                .build();

        ArduinoBoard savedArduinoBoard = this.arduinoBoardRepository.save(arduinoBoard);

        this.firebaseRestClient.addArduinoBoardToFirebaseDb(farmId, savedArduinoBoard.getId());

        return this.convertToDto(savedArduinoBoard);
    }

    @Override
    public ArduinoBoardDto getArduinoBoardById(int farmId, int arduinoBoardId) {
        User currentUser = this.userService.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(arduinoBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("Arduino Board", "id", arduinoBoardId));

        return this.convertToDto(arduinoBoard);
    }

    @Override
    public List<ArduinoBoardDto> getAllArduinoBoardsByFarmId(int farmId) {
        User currentUser = this.userService.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        List<ArduinoBoard> arduinoBoards = this.arduinoBoardRepository.findAllByFarmId(farmId).get();

        return arduinoBoards.stream().map((arduinoBoard -> this.convertToDto(arduinoBoard))).toList();
    }

    @Override
    public void deleteArduinoBoardById(int farmId, int arduinoBoardId) {
        User currentUser = this.userService.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(arduinoBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("Arduino Board", "id", arduinoBoardId));

        if (farm.getMainArduinoBoard() == arduinoBoard) {
            farm.setMainArduinoBoard(null);
        }

        farm.getArduinoBoards().remove(arduinoBoard);

        this.firebaseRestClient.deleteArduinoBoardFromFirebaseDb(farmId, arduinoBoardId);

        this.arduinoBoardRepository.delete(arduinoBoard);
    }

    @Override
    public ArduinoBoardDto convertToDto(ArduinoBoard arduinoBoard) {
        return ArduinoBoardDto
                .builder()
                .id(arduinoBoard.getId())
                .name(arduinoBoard.getName())
                .status(arduinoBoard.getStatus().name())
                .build();
    }


    private boolean isValidFarmAccess(User currentUser, Farm farm) {
        return currentUser.getFarms().contains(farm);
    }
}
