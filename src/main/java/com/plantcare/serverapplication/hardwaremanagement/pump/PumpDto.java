package com.plantcare.serverapplication.hardwaremanagement.pump;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PumpDto {
    private int id;
    private String name;
    private String status;
}
