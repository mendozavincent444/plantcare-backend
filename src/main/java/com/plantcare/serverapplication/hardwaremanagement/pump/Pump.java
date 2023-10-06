package com.plantcare.serverapplication.hardwaremanagement.pump;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pump")
public class Pump {
    @Id
    private int id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
