package com.plantcare.serverapplication.farmmanagement.plant;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlantDto {

    private int id;
    @Size(max = 50)
    private String name;

    private BigDecimal maximumEc;

    private BigDecimal maximumPh;

    private BigDecimal minimumEc;

    private BigDecimal minimumPh;

    @Size(max = 20)
    private String daysToMaturity;
}
