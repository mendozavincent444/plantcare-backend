package com.plantcare.serverapplication.farmmanagement.plant;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "maximum_ec", nullable = false)
    private BigDecimal maximumEc;

    @Column(name = "maximum_ph", nullable = false)
    private BigDecimal maximumPh;

    @Column(name = "minimum_ec", nullable = false)
    private BigDecimal minimumEc;

    @Column(name = "minimum_ph", nullable = false)
    private BigDecimal minimumPh;

    @Column(name = "days_to_maturity", nullable = false, length = 20)
    private String daysToMaturity;

    @ManyToOne
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;
}
