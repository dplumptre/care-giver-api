package com.overallheuristic.care_giver.repositories;


import com.overallheuristic.care_giver.config.AppRole;
import com.overallheuristic.care_giver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
