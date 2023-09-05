package com.plantcare.serverapplication.farmmanagement.container;

import java.util.List;

public interface ContainerService {
    ContainerDto addContainer(ContainerDto containerDto);

    List<ContainerDto> getAllContainersByFarmId(int farmId);

    void deleteContainerListById(List<Integer> containerIds);
}
