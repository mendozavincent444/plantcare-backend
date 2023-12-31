package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.shared.UserDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    private int id;

    @NotNull
    private Date datePlanted;
    @NotNull
    private Date harvestDate;
    @NotNull
    @Size(max = 20)
    private String status;
    @NotNull
    private int plantId;
    private String plantName;
    @NotNull
    private int containerId;
    @NotNull
    private int farmId;
    private int numberOfTasks;
    private UserDto farmer;
}
