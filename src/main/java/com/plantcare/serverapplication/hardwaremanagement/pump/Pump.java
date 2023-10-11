package com.plantcare.serverapplication.hardwaremanagement.pump;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pump")
public class Pump {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
