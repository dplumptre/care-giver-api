package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.payload.EmailRequestDto;
import com.overallheuristic.care_giver.dto.payload.ResetRequestDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.PasswordResetToken;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.repositories.TokenRespository;
import com.overallheuristic.care_giver.repositories.UserRepository;
import com.overallheuristic.care_giver.service.EmailService;
import com.overallheuristic.care_giver.service.PasswordResetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetServiceImp implements PasswordResetService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRespository tokenRespository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRespository tokenRepository;


    @Override
    public String passwordReset(EmailRequestDto email) {

        Optional<User> userOpt = userRepository.findByEmail(email.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Generate 6-digit code
            String code = generateSixDigitCode();
            // Save token to DB with expiry and user ref
            PasswordResetToken token = new PasswordResetToken();
            token.setUser(user);
            token.setCode(code);
            token.setExpiryDate(LocalDateTime.now().plusMinutes(10));
            tokenRespository.save(token);
            emailService.sendPasswordResetCode(email.getEmail(), code);
        }
        return "If your email is registered, you will receive a password reset code shortly.";
    }



    private String generateSixDigitCode() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000); // 100000 to 999999
        return String.valueOf(number);
    }




    @Override
    @Transactional
    public String passwordResetByCode(ResetRequestDto resetDto,String email) {
        if (!resetDto.getPassword().equals(resetDto.getConfirmPassword())) {
            throw new APIException("Passwords do not match");
        }

        Optional<User> userDetail = userRepository.findByEmail(email);
        if (userDetail.isEmpty()) {
            throw new APIException("An error occurred!");
        }

        PasswordResetToken tokenDetails = tokenRepository.findByCodeAndUserAndExpiryDateAfter(
                resetDto.getCode(), userDetail.get(), LocalDateTime.now()
        );

        if (tokenDetails == null) {
            throw new APIException("Invalid or expired code");
        }

        User user = userDetail.get();
        user.setPassword(passwordEncoder.encode(resetDto.getPassword()));
        userRepository.save(user);
        // delete token
        tokenRespository.delete(tokenDetails);


        return "Password reset successfully.";


    }
}
