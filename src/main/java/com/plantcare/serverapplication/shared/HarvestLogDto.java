package com.plantcare.serverapplication.shared;

import com.plantcare.serverapplication.farmmanagement.task.TaskDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class HarvestLogDto {
    private int id;
    private Date harvestedDate;
    private String plantName;
    private String farmerLastName;
}
