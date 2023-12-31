package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import java.util.List;

public interface ArduinoBoardService {
    ArduinoBoardDto addArduinoBoard(ArduinoBoardDto arduinoBoardDto, int farmId);

    ArduinoBoardDto getArduinoBoardById(int farmId, int arduinoBoardId);

    ArduinoBoardDto convertToDto(ArduinoBoard arduinoBoard);

    List<ArduinoBoardDto> getAllArduinoBoardsByFarmId(int farmId);
    void deleteArduinoBoardById(int farmId, int arduinoBoardId);
}
