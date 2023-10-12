package com.plantcare.serverapplication.hardwaremanagement.sensor;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.shared.DeviceStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeviceStatus status;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_type_id")
    private SensorType sensorType;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
