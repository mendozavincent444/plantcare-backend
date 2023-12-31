package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.shared.MessageResponseDto;

import java.util.List;

public interface ContainerService {
    ContainerDto addContainer(ContainerDto containerDto, int farmId);

    List<ContainerDto> getAllContainersByFarmId(int farmId);

    void deleteContainerListById(DeleteContainersDto deleteContainersDto, int farmId);

    ContainerDto updateContainer(ContainerDto containerDto, int farmId, int containerId);
}
