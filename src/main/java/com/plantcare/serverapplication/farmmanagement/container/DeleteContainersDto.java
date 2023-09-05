package com.plantcare.serverapplication.farmmanagement.container;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class DeleteContainersDto {
    private List<Integer> containerIds;
}
