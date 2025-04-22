package com.overallheuristic.care_giver.utils;

import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    UserRepository userRepository;

    public User loggedInUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserName(authentication.getName()).orElseThrow( ()-> new APIException("user nor Found!"));
    }

}
