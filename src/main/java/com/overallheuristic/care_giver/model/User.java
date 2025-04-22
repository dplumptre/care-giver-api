package com.overallheuristic.care_giver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;



@Entity
@Data
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
        })
public class User {


    public User(String userName,String name, String email, String password) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
    }




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Username is required")
    @Size(max = 20)
    @Column(name = "username")
    private String userName;

    @NotBlank(message = "Name is required")
    @Size(max = 20)
    private String name;

    @NotBlank(message = "Email is required")
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<UserProgressLearningHub> userProgress = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<ExerciseSession> exerciseSessions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<UserBadge> userBadges = new ArrayList<>();



    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


@Setter
@Getter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


}
