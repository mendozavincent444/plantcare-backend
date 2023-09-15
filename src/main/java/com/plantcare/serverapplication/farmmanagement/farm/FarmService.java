package com.plantcare.serverapplication.farmmanagement.farm;

import com.plantcare.serverapplication.security.service.UserDetailsImpl;
import com.plantcare.serverapplication.shared.UserDto;
import com.plantcare.serverapplication.shared.UserInfoResponseDto;
import com.plantcare.serverapplication.usermanagement.user.User;

import java.util.List;

public interface FarmService {

    FarmDto getFarmById(int farmId);

    FarmDto addFarm(FarmDto farmDto);

    List<FarmDto> getAllFarms();

    void deleteFarmById(int farmId);

    FarmDto updateFarm(FarmDto farmDto, int farmId);

    List<UserDto> getAllFarmersByFarmId(int farmId);
}
