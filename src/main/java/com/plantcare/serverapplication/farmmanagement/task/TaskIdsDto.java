package com.plantcare.serverapplication.farmmanagement.task;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TaskIdsDto {
    List<Integer> taskIds;
}
