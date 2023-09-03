package com.plantcare.serverapplication.usermanagement.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Role {
    @Id
    private int id;
    @Column(name = "role_name", nullable = false)
    private String roleName;
}
