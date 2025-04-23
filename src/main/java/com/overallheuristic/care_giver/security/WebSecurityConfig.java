package com.overallheuristic.care_giver.security;


import com.overallheuristic.care_giver.config.AppRole;
import com.overallheuristic.care_giver.model.*;
import com.overallheuristic.care_giver.repositories.*;
import com.overallheuristic.care_giver.security.jwt.AuthEntryPointJwt;
import com.overallheuristic.care_giver.security.jwt.AuthTokenFilter;
import com.overallheuristic.care_giver.security.services.UserDetailsServiceImpl;
import com.overallheuristic.care_giver.utils.enums.VideoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/auth/signup").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                //.requestMatchers("/api/admin/**").permitAll()
                                //.requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers(headers -> headers.frameOptions(
                frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"));
    }


    @Bean
    public CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            LevelRepository levelRepository,
            VideoRepository videoRepository,
            BadgeRepository badgeRepository,
            TaskRepository taskRepository
    ) {
        return args -> {
            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUserRole);
                    });

            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_SELLER);
                        return roleRepository.save(newSellerRole);
                    });

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);


            // Create users if not already present
            if (!userRepository.existsByUserName("john")) {
                User user1 = new User("john", "John Doe","user@example.com", passwordEncoder.encode("password"));
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("seller")) {
                User seller1 = new User("seller", "Seller Man","seller1@example.com", passwordEncoder.encode("password2"));
                userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin",  "Joseph","admin@example.com",passwordEncoder.encode("adminPass"));
                userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUserName("john").ifPresent(user -> {
                user.setRoles(userRoles);
                userRepository.save(user);
            });

            userRepository.findByUserName("seller").ifPresent(seller -> {
                seller.setRoles(sellerRoles);
                userRepository.save(seller);
            });

            userRepository.findByUserName("admin").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });


//            String[] levelNames = {
//                    "Bronze", "Silver", "Gold", "Platinum", "Diamond",
//                    "Emerald", "Ruby", "Sapphire", "Master", "Champion"
//            };
//
//            for (int i = 0; i < levelNames.length; i++) {
//                String levelName = levelNames[i];
//
//                // Check if level with the same name exists
//                if (levelRepository.findByLevelName(levelName).isEmpty()) {
//                    Level level = new Level();
//                    level.setLevelName(levelName);
//                    level.setLevelNumber(i + 1);
//                    level.setScore(100);
//                    levelRepository.save(level);
//                }
//            }




// Videos

//            String[] titles = {
//                    "What is Stroke",
//                    "Caregiver Guide  Move Stroke Survivors Safely",
//                    "Exercise For Stroke Survivors",
//                    "Self Care for Caregivers  5 Minute Routine",
//            };
//
//            String[] linkCodes = {
//                    "No7eawZMmWo",
//                    "w5DvsZAmH-A",
//                    "pxyh7fWi4rs",
//                    "eo6w7muVrmo"
//            };
//
//            VideoType[] videoTypes = {
//                    VideoType.LEARNING_HUB,
//                    VideoType.LEARNING_HUB,
//                    VideoType.PATIENT_EXERCISE_SUPPORT,
//                    VideoType.CARER_EXERCISE
//            };
//
//            for (int i = 0; i < titles.length; i++) {
//                String title = titles[i];
//                String linkCode = linkCodes[i];
//                VideoType videoType = videoTypes[i];
//
//                 Video myVee = videoRepository.findByTitle(title);
//                if (myVee == null) {
//                    Video video = new Video();
//                    video.setTitle(title);
//                    video.setLink(linkCode);
//                    video.setVideoType(videoType);
//                    video.setDescription("goal is to help them feel supported from head to toe");
//                    videoRepository.save(video);
//                }
//            }




//
//
//            Badge badge1 = Badge.builder()
//                    .name("Carer 1-Day Streak")
//                    .videoType(VideoType.CARER_EXERCISE)
//                    .streakDays(1)
//                    .icon("carer_1d.png")
//                    .build();
//
//            Badge badge2 = Badge.builder()
//                    .name("Carer 7-Day Streak")
//                    .videoType(VideoType.CARER_EXERCISE)
//                    .streakDays(7)
//                    .icon("carer_7d.png")
//                    .build();
//
//            Badge badge3 = Badge.builder()
//                    .name("Patient 1-Day Streak")
//                    .videoType(VideoType.PATIENT_EXERCISE_SUPPORT)
//                    .streakDays(1)
//                    .patientRequired(1)
//                    .icon("patient_1d.png")
//                    .build();
//
//            Badge badge4 = Badge.builder()
//                    .name("Patient 7-Day Streak")
//                    .videoType(VideoType.PATIENT_EXERCISE_SUPPORT)
//                    .streakDays(7)
//                    .patientRequired(1)
//                    .icon("patient_7d.png")
//                    .build();
//
//            Badge badge5 = Badge.builder()
//                    .name("Carer Medication Streak")
//                    .videoType(VideoType.MEDICATION)
//                    .streakDays(7)
//                    .icon("meds_7d.png")
//                    .build();
//
//            List<Badge> demoBadges = List.of(badge1, badge2, badge3, badge4, badge5);
//
//            for (Badge badge : demoBadges) {
//                boolean exists = badgeRepository
//                        .findByNameAndVideoTypeAndStreakDays(
//                                badge.getName(),
//                                badge.getVideoType(),
//                                badge.getStreakDays()
//                        ).isPresent();
//
//                if (!exists) {
//                    badgeRepository.save(badge);
//                }
//            }
        };
    }

}