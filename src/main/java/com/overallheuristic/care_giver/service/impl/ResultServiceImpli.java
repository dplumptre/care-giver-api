package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.ResultDto;
import com.overallheuristic.care_giver.model.Badge;
import com.overallheuristic.care_giver.model.HomeSetupResult;
import com.overallheuristic.care_giver.model.MedicationLog;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.repositories.*;
import com.overallheuristic.care_giver.service.ResultService;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import com.overallheuristic.care_giver.utils.enums.MedicationAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ResultServiceImpli implements ResultService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HomeSetupResultRepository homeSetupResultRepository;
    @Autowired
    private ExerciseSessionRepository exerciseSessionRepository;
    @Autowired
    private MedicationLogRepository medicationLogRepository;
    @Autowired
    private BadgeRepository badgeRepository;



    @Override
    public ResultDto getResults(Long userId) {

        int homeSetupResult = 0;
        int patientCount = 0;
        int carerCount =0;
        int medicalStars =0;
        String medicalAdherenceBadge="";

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<HomeSetupResult> response = homeSetupResultRepository.findAllByCarerAndIsCompletedIsTrue(user);

        if(!response.isEmpty()) {
           homeSetupResult = response.size();
        }

        patientCount = exerciseSessionRepository.countByUserAndPatientIsNotNull(user).intValue();
        carerCount = exerciseSessionRepository.countByUserAndPatientIsNull(user).intValue();
        List<MedicationLog> logs = medicationLogRepository.findByPatientUser(user);

        Set<LocalDate> takeDays = new HashSet<>();

        for (MedicationLog log : logs) {
            if (MedicationAction.TAKE.equals(log.getAction())) {
                medicalStars++;
                takeDays.add(log.getScheduledFor().toLocalDate());
            }
        }

        int currentStreak = 0;
        if (!takeDays.isEmpty()) {
            List<LocalDate> sortedDays = takeDays.stream().sorted().toList();

            currentStreak = 1;
            int longestStreak = 1;

            for (int i = 1; i < sortedDays.size(); i++) {
                LocalDate prev = sortedDays.get(i - 1);
                LocalDate curr = sortedDays.get(i);

                if (curr.equals(prev.plusDays(1))) {
                    currentStreak++;
                } else {
                    currentStreak = 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        List<Badge> earnedBadges = badgeRepository.findByActivityTypeAndStreakDaysLessThanEqual(
                ActivityType.MEDICATION, currentStreak
        );

        medicalAdherenceBadge = earnedBadges.stream()
                .max(Comparator.comparingInt(Badge::getStreakDays))
                .map(Badge::getName)
                .orElse("none");

        ResultDto resultDto = new ResultDto();
        resultDto.setCarerStars(carerCount);
        resultDto.setPatientStars(patientCount);
        resultDto.setHomeSetupStars(homeSetupResult);
        resultDto.setMedicalStars(medicalStars);
        resultDto.setMedicalAdherenceBadge(medicalAdherenceBadge);

        return resultDto;
    }
}
