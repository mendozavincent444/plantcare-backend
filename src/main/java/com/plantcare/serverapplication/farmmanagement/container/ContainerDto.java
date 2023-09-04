package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.farmmanagement.task.Task;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContainerDto {

    private int id;

    @Size(max = 20)
    private String name;

    private int arduinoBoardId;

    private int plantId;

    private List<Task> tasks;
}
