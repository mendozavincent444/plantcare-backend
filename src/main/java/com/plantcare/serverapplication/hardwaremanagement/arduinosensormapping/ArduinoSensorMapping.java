package com.plantcare.serverapplication.hardwaremanagement.arduinosensormapping;

import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoard;
import com.plantcare.serverapplication.hardwaremanagement.sensor.Sensor;
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
@Table(name = "arduino_sensor_mapping")
public class ArduinoSensorMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "arduino_board_id", nullable = false)
    private ArduinoBoard arduinoBoard;

    @OneToOne
    @JoinColumn(name = "sensor_id", nullable = false, unique = true)
    private Sensor sensor;
}
