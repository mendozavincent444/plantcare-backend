package com.plantcare.serverapplication.hardwaremanagement.sensor;

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
@Table(name = "sensor_type")
public class SensorType {

    @Id
    private int id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;
}
