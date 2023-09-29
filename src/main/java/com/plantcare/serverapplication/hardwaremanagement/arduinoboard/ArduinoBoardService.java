package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

public interface ArduinoBoardService {

    ArduinoBoardDto getArduinoBoardById(int arduinoBoardId);

    ArduinoBoardDto mapToDto(ArduinoBoard arduinoBoard);

}
