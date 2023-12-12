package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.firebase.FirebaseRestClient;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.DeviceStatus;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
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

    public ArduinoBoardServiceImpl(
            ArduinoBoardRepository arduinoBoardRepository,
            UserRepository userRepository,
            FarmRepository farmRepository,
            FirebaseRestClient firebaseRestClient
    ) {
        this.arduinoBoardRepository = arduinoBoardRepository;
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.firebaseRestClient = firebaseRestClient;
    }

    @Override
    public ArduinoBoardDto addArduinoBoard(ArduinoBoardDto arduinoBoardDto, int farmId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
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
        User currentUser = this.getCurrentUser();

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
        User currentUser = this.getCurrentUser();

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
        User currentUser = this.getCurrentUser();

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

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
