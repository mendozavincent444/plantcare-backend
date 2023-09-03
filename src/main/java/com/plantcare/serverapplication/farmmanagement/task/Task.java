package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.farmmanagement.container.Container;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_planted", nullable = false)
    private Date datePlanted;

    @Column(name = "harvest_date", nullable = false)
    private Date harvestDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne
    @JoinColumn(name = "container_id")
    private Container container;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
