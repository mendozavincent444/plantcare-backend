package com.plantcare.serverapplication.farmmanagement.harvestlog;

import com.plantcare.serverapplication.farmmanagement.farm.Farm;
import com.plantcare.serverapplication.farmmanagement.task.Task;
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
@Table(name = "harvest_log")
public class HarvestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "harvested_date", nullable = false)
    private Date harvestedDate;

    @Column(name = "plant_name", nullable = false)
    private String plantName;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
