package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.hardwaremanagement.sensor.Sensor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final ModelMapper modelMapper;

    public FarmServiceImpl(FarmRepository farmRepository, ModelMapper modelMapper) {
        this.farmRepository = farmRepository;
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


    private FarmDto mapToDto(Farm farm) {
        return this.modelMapper.map(farm, FarmDto.class);
    }

    private Farm mapToEntity(FarmDto farmDto) {
        return this.modelMapper.map(farmDto, Farm.class);
    }
}
