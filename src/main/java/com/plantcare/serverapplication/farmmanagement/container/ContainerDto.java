package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.farmmanagement.task.Task;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContainerDto {

    private int id;

    @Size(max = 20)
    private String name;

    private int arduinoBoardId;

    private int plantId;

    private int farmId;

    private List<Task> tasks;
}
