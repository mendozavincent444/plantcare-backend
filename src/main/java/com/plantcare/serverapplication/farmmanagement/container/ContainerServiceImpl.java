package com.plantcare.serverapplication.farmmanagement.container;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Service
public class ContainerServiceImpl implements ContainerService {


    private final ContainerRepository containerRepository;
    private final ModelMapper modelMapper;

    public ContainerServiceImpl(ContainerRepository containerRepository, ModelMapper modelMapper) {
        this.containerRepository = containerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ContainerDto addContainer(ContainerDto containerDto) {
        return new ContainerDto();
    }
}
