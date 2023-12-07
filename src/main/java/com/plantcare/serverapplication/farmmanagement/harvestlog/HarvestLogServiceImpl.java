package com.plantcare.serverapplication.farmmanagement.harvestlog;

import com.plantcare.serverapplication.exception.ResourceNotFoundException;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.farm.FarmRepository;
import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.HarvestLogDto;
import com.plantcare.serverapplication.usermanagement.user.User;
import com.plantcare.serverapplication.usermanagement.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
@Service
public class HarvestLogServiceImpl implements HarvestLogService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final HarvestLogRepository harvestLogRepository;

    public HarvestLogServiceImpl(
            FarmRepository farmRepository,
            UserRepository userRepository,
            HarvestLogRepository harvestLogRepository
    ) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.harvestLogRepository = harvestLogRepository;
    }

    @Override
    public List<HarvestLogDto> getAllHarvestLogsByFarmId(int farmId) {

        Farm farm = this.farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", farmId));

        User currentUser = this.getCurrentUser();

        if (!isValidFarmAccess(currentUser, farm)) {
            throw new ResourceAccessException("User access to resource is forbidden");
        }

        List<HarvestLog> harvestLogs = farm.getHarvestLogs();

        return harvestLogs.stream().map((harvestLog -> this.convertToDto(harvestLog))).toList();
    }


    private HarvestLogDto convertToDto(HarvestLog harvestLog) {
        return HarvestLogDto
                .builder()
                .id(harvestLog.getId())
                .plantName(harvestLog.getPlantName())
                .harvestedDate(harvestLog.getHarvestedDate())
                .farmerLastName(harvestLog.getFarmer().getLastName())
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
