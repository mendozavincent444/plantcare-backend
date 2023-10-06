package com.plantcare.serverapplication.farmmanagement.task;

import com.plantcare.serverapplication.farmmanagement.container.Container;
import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.plant.Plant;
import com.plantcare.serverapplication.usermanagement.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @OneToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @ManyToOne
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;
}
