package com.plantcare.serverapplication.farmmanagement.harvestlog;

import com.plantcare.serverapplication.farmmanagement.task.Task;
import com.plantcare.serverapplication.usermanagement.user.User;
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
@Table(name = "harvest_log")
public class HarvestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "harvested_date", nullable = false)
    private Date harvestedDate;

    @OneToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // EDIT ERD RELATIONSHIP TO USER
    @OneToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;
}
