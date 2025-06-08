package com.overallheuristic.care_giver.service;

public interface EmailService {
    public default void sendPasswordResetCode(String email, String code) {
    }
}
