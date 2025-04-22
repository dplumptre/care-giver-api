package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByNameOrPhone(@NotBlank @Size(min = 3) String name, @NotBlank @Size(min = 3) String name1);

    Collection<Object> findAllByUser(User user);
}
