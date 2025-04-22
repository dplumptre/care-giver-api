package com.overallheuristic.care_giver.utils;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class StreakCalculator {

    public static int calculateStreak(List<LocalDate> dates) {
        if (dates == null || dates.isEmpty()) return 0;

        Collections.sort(dates);
        int streak = 0;
        LocalDate current = LocalDate.now();

        for (int i = dates.size() - 1; i >= 0; i--) {
            if (dates.get(i).isEqual(current)) {
                streak++;
                current = current.minusDays(1);
            } else if (dates.get(i).isBefore(current)) {
                break;
            }
        }
        return streak;
    }
}

