package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ArduinoBoardServiceImpl implements ArduinoBoardService {

    private final ArduinoBoardRepository arduinoBoardRepository;
    private final ModelMapper modelMapper;

    public ArduinoBoardServiceImpl(ArduinoBoardRepository arduinoBoardRepository, ModelMapper modelMapper) {
        this.arduinoBoardRepository = arduinoBoardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArduinoBoardDto getArduinoBoardById(int arduinoBoardId) {
        ArduinoBoard arduinoBoard = this.arduinoBoardRepository.findById(arduinoBoardId).orElseThrow();

        return this.mapToDto(arduinoBoard);
    }

    private ArduinoBoardDto mapToDto(ArduinoBoard arduinoBoard) {
        return this.modelMapper.map(arduinoBoard, ArduinoBoardDto.class);
    }

    private ArduinoBoard mapToEntity(ArduinoBoardDto arduinoBoardDto) {
        return this.modelMapper.map(arduinoBoardDto, ArduinoBoard.class);
    }
}
