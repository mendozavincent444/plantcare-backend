package com.plantcare.serverapplication.farmmanagement.plant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
