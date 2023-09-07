package com.plantcare.serverapplication.farmmanagement.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskOperationDto {

    private List<TaskDto> taskDtoList;
}
