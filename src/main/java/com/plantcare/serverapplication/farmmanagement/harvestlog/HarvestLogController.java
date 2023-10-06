package com.plantcare.serverapplication.farmmanagement.harvestlog;

import com.plantcare.serverapplication.shared.HarvestLogDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farms/{farmId}/harvest-logs")
public class HarvestLogController {
    private final HarvestLogService harvestLogService;

    public HarvestLogController(HarvestLogService harvestLogService) {
        this.harvestLogService = harvestLogService;
    }

    @GetMapping
    public ResponseEntity<List<HarvestLogDto>> getHarvestLogsByFarmId(@PathVariable int farmId) {

        List<HarvestLogDto> harvestLogDtos = this.harvestLogService.getAllHarvestLogsByFarmId(farmId);

        return new ResponseEntity<>(harvestLogDtos, HttpStatus.OK);
    }
}
