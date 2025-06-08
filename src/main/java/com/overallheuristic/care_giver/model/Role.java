package com.overallheuristic.care_giver.model;

import com.overallheuristic.care_giver.config.AppRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")

    private Integer roleId;



    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")

    private AppRole roleName;


    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
}
