package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.PasswordResetToken;
import com.overallheuristic.care_giver.model.Task;
import com.overallheuristic.care_giver.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRespository extends JpaRepository<PasswordResetToken, Long> {

    void findByCodeAndUser(@NotBlank @Max(6) @Min(6) String code, Optional<User> user);

    PasswordResetToken findByCodeAndUserAndExpiryDateAfter(@NotBlank @Max(6) @Min(6) String code, User user, LocalDateTime now);
}