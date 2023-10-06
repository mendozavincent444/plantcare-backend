package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import java.util.List;

public interface ArduinoBoardService {

    ArduinoBoardDto getArduinoBoardById(int arduinoBoardId);

    ArduinoBoardDto mapToDto(ArduinoBoard arduinoBoard);

    List<ArduinoBoardDto> getAllArduinoBoardsByFarmId(int farmId);
}
