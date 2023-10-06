package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class ArduinoBoardServiceImpl implements ArduinoBoardService {

    private final ArduinoBoardRepository arduinoBoardRepository;
    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final ModelMapper modelMapper;

    public ArduinoBoardServiceImpl(
            ArduinoBoardRepository arduinoBoardRepository,
            UserRepository userRepository,
            FarmRepository farmRepository,
            ModelMapper modelMapper
    ) {
        this.arduinoBoardRepository = arduinoBoardRepository;
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArduinoBoardDto getArduinoBoardById(int arduinoBoardId) {
        ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(arduinoBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("Arduino Board", "id", arduinoBoardId));

        return this.mapToDto(arduinoBoard);
    }

    @Override
    public ArduinoBoardDto mapToDto(ArduinoBoard arduinoBoard) {
        return this.modelMapper.map(arduinoBoard, ArduinoBoardDto.class);
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

    private ArduinoBoardDto convertToDto(ArduinoBoard arduinoBoard) {
        return ArduinoBoardDto
                .builder()
                .id(arduinoBoard.getId())
                .name(arduinoBoard.getName())
                .status(arduinoBoard.getStatus())
                .build();
    }

    private ArduinoBoard mapToEntity(ArduinoBoardDto arduinoBoardDto) {
        return this.modelMapper.map(arduinoBoardDto, ArduinoBoard.class);
    }

    private boolean isValidFarmAccess(User currentUser, Farm farm) {
        return currentUser.getFarms().contains(farm);
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
