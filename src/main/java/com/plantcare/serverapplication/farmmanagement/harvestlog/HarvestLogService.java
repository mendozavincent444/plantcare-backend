package com.plantcare.serverapplication.farmmanagement.harvestlog;

import com.plantcare.serverapplication.shared.HarvestLogDto;

import java.util.List;

public interface HarvestLogService {

    List<HarvestLogDto> getAllHarvestLogsByFarmId(int farmId);
}
