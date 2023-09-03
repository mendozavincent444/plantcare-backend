package com.plantcare.serverapplication.hardwaremanagement.arduinoboard;

import com.plantcare.serverapplication.hardwaremanagement.arduinosensormapping.ArduinoSensorMapping;
import com.plantcare.serverapplication.hardwaremanagement.sensor.Sensor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "arduino_board")
public class ArduinoBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "arduinoBoard")
    private Set<ArduinoSensorMapping> sensorMappings;
}
