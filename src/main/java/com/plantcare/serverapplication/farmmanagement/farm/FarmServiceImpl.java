package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.hardwaremanagement.sensor.Sensor;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FarmServiceImpl(FarmRepository farmRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
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

    @Override
    public FarmDto addFarm(FarmDto farmDto) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Farm farm = Farm
                .builder()
                .name(farmDto.getName())
                .location(farmDto.getLocation())
                .build();

        currentUser.getFarms().add(farm);
        Farm savedFarm = this.farmRepository.save(farm);

        return this.mapToDto(savedFarm);
    }


    private FarmDto mapToDto(Farm farm) {
        return this.modelMapper.map(farm, FarmDto.class);
    }

    private Farm mapToEntity(FarmDto farmDto) {
        return this.modelMapper.map(farmDto, Farm.class);
    }
}
