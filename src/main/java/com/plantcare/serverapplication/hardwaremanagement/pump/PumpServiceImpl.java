package com.plantcare.serverapplication.hardwaremanagement.pump;

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
public class PumpServiceImpl implements PumpService {
    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final PumpRepository pumpRepository;

    public PumpServiceImpl(
            UserRepository userRepository,
            FarmRepository farmRepository,
            PumpRepository pumpRepository
    ) {
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.pumpRepository = pumpRepository;
    }

    @Override
    public List<PumpDto> getAllPumpsByFarmId(int farmId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        List<Pump> pumps = this.pumpRepository.getAllPumpsByFarmId(farmId).get();

        return pumps.stream().map(pump -> this.convertToDto(pump)).toList();
    }

    @Override
    public PumpDto getPumpById(int farmId, int pumpId) {
        User currentUser = this.getCurrentUser();

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        Pump pump = this.pumpRepository.findById(pumpId)
                .orElseThrow(() -> new ResourceNotFoundException("Pump", "id", pumpId));

        return this.convertToDto(pump);
    }


    private PumpDto convertToDto(Pump pump) {
        return PumpDto
                .builder()
                .id(pump.getId())
                .name(pump.getName())
                .status(pump.getStatus())
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
