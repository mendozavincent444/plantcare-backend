package com.plantcare.serverapplication.farmmanagement.container;

import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import com.plantcare.serverapplication.farmmanagement.task.Task;
import com.plantcare.serverapplication.hardwaremanagement.arduinoboard.ArduinoBoard;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "container")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @OneToOne
    @JoinColumn(name = "arduino_board_id")
    private ArduinoBoard arduinoBoard;

    @OneToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "container")
    private List<Task> tasks;
}
