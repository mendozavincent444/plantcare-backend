package com.plantcare.serverapplication.farmmanagement.container;

import java.util.List;

public interface ContainerService {
    void addContainer(ContainerDto containerDto);

    List<ContainerDto> getAllContainersByFarmId(int farmId);

    void deleteContainerListById(DeleteContainersDto deleteContainersDto, int farmId);

    ContainerDto updateContainer(ContainerDto containerDto, int containerId);
}
