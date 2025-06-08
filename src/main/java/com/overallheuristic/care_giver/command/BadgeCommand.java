package com.overallheuristic.care_giver.command;

import com.overallheuristic.care_giver.model.Badge;
import com.overallheuristic.care_giver.repositories.BadgeRepository;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(5)
public class BadgeCommand implements CommandLineRunner {

    @Autowired
    private BadgeRepository badgeRepository;

    @Override
    public void run(String... args) throws Exception {


        Badge badge1 = Badge.builder()
                .name("Carer 1-Day Streak")
                .activityType(ActivityType.CARER_EXERCISE)
                .streakDays(1)
                .icon("id-badge")
                .build();

        Badge badge2 = Badge.builder()
                .name("Carer 7-Day Streak")
                .activityType(ActivityType.CARER_EXERCISE)
                .streakDays(7)
                .icon("id-badge")
                .build();

        Badge badge3 = Badge.builder()
                .name("Patient 1-Day Streak")
                .activityType(ActivityType.PATIENT_EXERCISE_SUPPORT)
                .streakDays(1)
                .patientRequired(1)
                .icon("id-badge")
                .build();

        Badge badge4 = Badge.builder()
                .name("Patient 7-Day Streak")
                .activityType(ActivityType.PATIENT_EXERCISE_SUPPORT)
                .streakDays(7)
                .patientRequired(1)
                .icon("id-badge")
                .build();

        Badge badge5 = Badge.builder()
                .name("Starter Adherence Badge")
                .activityType(ActivityType.MEDICATION)
                .streakDays(1)
                .icon("award")
                .build();



        Badge badge6 = Badge.builder()
                .name("Bronze Adherence Badge")
                .activityType(ActivityType.MEDICATION)
                .streakDays(3)
                .icon("award")
                .build();

        Badge badge7 = Badge.builder()
                .name("Silver Adherence Badge")
                .activityType(ActivityType.MEDICATION)
                .streakDays(7)
                .icon("award")
                .build();

        Badge badge8 = Badge.builder()
                .name("Gold Adherence Badge")
                .activityType(ActivityType.MEDICATION)
                .streakDays(21)
                .icon("award")
                .build();

        List<Badge> demoBadges = List.of(badge1, badge2, badge3, badge4, badge5,badge6,badge7,badge8);

        for (Badge badge : demoBadges) {
            boolean exists = badgeRepository
                    .findByNameAndActivityTypeAndStreakDays(
                            badge.getName(),
                            badge.getActivityType(),
                            badge.getStreakDays()
                    ).isPresent();

            if (!exists) {
                badgeRepository.save(badge);
            }
        }
    }
}
