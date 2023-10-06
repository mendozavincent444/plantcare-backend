package com.plantcare.serverapplication.hardwaremanagement.sensor;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    private final UserRepository userRepository;
    private final SensorRepository sensorRepository;
    private final FarmRepository farmRepository;

    public SensorServiceImpl(
            UserRepository userRepository,
            SensorRepository sensorRepository,
            FarmRepository farmRepository
    ) {
        this.userRepository = userRepository;
        this.sensorRepository = sensorRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public SensorDto convertToDto(Sensor sensor) {
        if (sensor == null) {
            return null;
        }

        return SensorDto
                .builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .status(sensor.getStatus())
                .sensorTypeName(sensor.getSensorType().getName())
                .build();
    }

    @Override
    public List<SensorDto> getAllSensorsByFarmId(int farmId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        List<Sensor> sensors = this.sensorRepository.getAllSensorsByFarmId(farmId).get();

        return sensors.stream().map((sensor -> this.convertToDto(sensor))).toList();
    }

    private boolean isValidFarmAccess(User currentUser, Farm farm) {
        return currentUser.getFarms().contains(farm);
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
