package com.plantcare.serverapplication.farmmanagement.task;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class DeleteTasksDto {
    private List<Integer> taskIds;
}
