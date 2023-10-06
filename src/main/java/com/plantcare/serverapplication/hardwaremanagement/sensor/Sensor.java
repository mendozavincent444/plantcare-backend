package com.plantcare.serverapplication.hardwaremanagement.sensor;

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
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_type_id")
    private SensorType sensorType;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
