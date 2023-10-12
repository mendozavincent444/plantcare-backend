package com.plantcare.serverapplication.hardwaremanagement.sensor;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.DeviceStatus;
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
    private final SensorTypeRepository sensorTypeRepository;
    private final FarmRepository farmRepository;

    public SensorServiceImpl(
            UserRepository userRepository,
            SensorRepository sensorRepository,
            SensorTypeRepository sensorTypeRepository,
            FarmRepository farmRepository
    ) {
        this.userRepository = userRepository;
        this.sensorRepository = sensorRepository;
        this.sensorTypeRepository = sensorTypeRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public SensorDto addSensor(SensorDto sensorDto, int farmId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        SensorType sensorType = this.sensorTypeRepository.getSensorTypeByName(sensorDto.getSensorTypeName()).get();

        Sensor sensor = Sensor
                .builder()
                .name(sensorDto.getName())
                .status(DeviceStatus.INACTIVE)
                .sensorType(sensorType)
                .farm(farm)
                .build();

        Sensor savedSensor = this.sensorRepository.save(sensor);

        return this.convertToDto(sensor);
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
                .status(sensor.getStatus().name())
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

    @Override
    public SensorDto getSensorById(int farmId, int sensorId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        Sensor sensor = this.sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor", "id", sensorId));

        return this.convertToDto(sensor);
    }

    @Override
    public void deleteSensorById(int farmId, int sensorId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        Sensor sensor = this.sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor", "id", sensorId));

        farm.getSensors().remove(sensor);

        this.sensorRepository.delete(sensor);
    }

    private boolean isValidFarmAccess(User currentUser, Farm farm) {
        return currentUser.getFarms().contains(farm);
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
    }
}
