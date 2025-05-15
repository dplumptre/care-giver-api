package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.ExerciseResultDto;
import com.overallheuristic.care_giver.dto.payload.ExerciseSessionRequestDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.*;
import com.overallheuristic.care_giver.repositories.BadgeRepository;
import com.overallheuristic.care_giver.repositories.ExerciseSessionRepository;
import com.overallheuristic.care_giver.repositories.PatientRepository;
import com.overallheuristic.care_giver.repositories.VideoRepository;
import com.overallheuristic.care_giver.service.ExerciseSessionService;
import com.overallheuristic.care_giver.utils.StreakCalculator;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExerciseSessionServiceImpl implements ExerciseSessionService {


    private final PatientRepository patientRepository;
    private final VideoRepository videoRepository;
    private final ExerciseSessionRepository exerciseSessionRepository;
    private final BadgeRepository badgeRepository;

    public ExerciseSessionServiceImpl(PatientRepository patientRepository, VideoRepository videoRepository, ExerciseSessionRepository exerciseSessionRepository, BadgeRepository badgeRepository) {
        this.patientRepository = patientRepository;
        this.videoRepository = videoRepository;
        this.exerciseSessionRepository = exerciseSessionRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public String completeSession(ExerciseSessionRequestDto exerciseSessionDto, Long patientId, User user) {
        Patient patient = null;
        if(patientId != null) {
            patient = patientRepository.findById(patientId).orElseThrow(  ()-> new APIException("Patient not found"));
        }
        Video video = videoRepository.findById(exerciseSessionDto.getVideoId()).orElseThrow(  ()-> new APIException("Video not found"));
        ExerciseSession exerciseSession = new ExerciseSession();
        exerciseSession.setCareGiver(exerciseSessionDto.isCareGiver());
        exerciseSession.setPatient(patient);
        exerciseSession.setVideo(video);
        exerciseSession.setUser(user);
        exerciseSessionRepository.save(exerciseSession);
        return "Exercise completed successfully";
    }

    @Override
    public ExerciseResultDto getExerciseResult(User user) {
        Long patientCount = exerciseSessionRepository.countByUserAndPatientIsNotNull(user);
        Long carerCount = exerciseSessionRepository.countByUserAndPatientIsNull(user);

        // Fetch and log patient session dates
        List<LocalDate> patientDates = exerciseSessionRepository.findDistinctExerciseDatesByUserAndPatientNotNull(user)
                .stream()
                .map(createdAt -> {
                    LocalDate date = createdAt.toLocalDate();
                    System.out.println("Patient Exercise Date: " + date);
                    return date;
                })
                .collect(Collectors.toList());

        // Fetch and log carer session dates
        List<LocalDate> carerDates = exerciseSessionRepository.findDistinctExerciseDatesByUserAndPatientIsNull(user)
                .stream()
                .map(createdAt -> {
                    LocalDate date = createdAt.toLocalDate();
                    System.out.println("Carer Exercise Date: " + date);
                    return date;
                })
                .collect(Collectors.toList());

        // Calculate streaks
        int patientStreak = StreakCalculator.calculateStreak(patientDates);
        int carerStreak = StreakCalculator.calculateStreak(carerDates);

        // Separate badges by video type
        List<Badge> badges = badgeRepository.findAll();

        Map<Integer, String> patientBadgeMap = new HashMap<>();
        Map<Integer, String> carerBadgeMap = new HashMap<>();

        for (Badge badge : badges) {
            if (badge.getActivityType() == ActivityType.PATIENT_EXERCISE_SUPPORT) {
                patientBadgeMap.put(badge.getStreakDays(), badge.getName());
            } else if (badge.getActivityType() == ActivityType.CARER_EXERCISE) {
                carerBadgeMap.put(badge.getStreakDays(), badge.getName());
            }
        }

        // Match earned badges by comparing streaks
        Map<Integer, String> matchedPatientStreaks = new HashMap<>();
        Map<Integer, String> matchedCarerStreaks = new HashMap<>();

        for (Integer streakDay : patientBadgeMap.keySet()) {
            if (patientStreak >= streakDay) {
                matchedPatientStreaks.put(streakDay, patientBadgeMap.get(streakDay));
            }
        }

        for (Integer streakDay : carerBadgeMap.keySet()) {
            if (carerStreak >= streakDay) {
                matchedCarerStreaks.put(streakDay, carerBadgeMap.get(streakDay));
            }
        }

        // Debug logs
//        System.out.println("Patient Count: " + patientCount);
//        System.out.println("Carer Count: " + carerCount);
//        System.out.println("Patient Streak: " + patientStreak);
//        System.out.println("Carer Streak: " + carerStreak);
//        System.out.println("Patient Badge Map: " + patientBadgeMap);
//        System.out.println("Carer Badge Map: " + carerBadgeMap);
//        System.out.println("Matched Patient Streaks: " + matchedPatientStreaks);
//        System.out.println("Matched Carer Streaks: " + matchedCarerStreaks);

        return new ExerciseResultDto(
                patientCount.intValue(),
                carerCount.intValue(),
                matchedPatientStreaks,
                matchedCarerStreaks
        );
    }




}
