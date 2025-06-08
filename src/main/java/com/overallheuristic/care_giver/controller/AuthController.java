package com.overallheuristic.care_giver.controller;


import com.overallheuristic.care_giver.config.AppRole;
import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.LoginDto;
import com.overallheuristic.care_giver.dto.SignupDto;
import com.overallheuristic.care_giver.dto.UserInfoResponse;
import com.overallheuristic.care_giver.dto.payload.EmailRequestDto;
import com.overallheuristic.care_giver.dto.payload.ResetRequestDto;
import com.overallheuristic.care_giver.model.Role;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.repositories.RoleRepository;
import com.overallheuristic.care_giver.repositories.UserRepository;
import com.overallheuristic.care_giver.security.jwt.JwtUtils;
import com.overallheuristic.care_giver.security.services.UserDetailsImpl;
import com.overallheuristic.care_giver.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PasswordResetService passwordResetService;



    @PostMapping("/signin")
    public ResponseEntity<APIResponse<?>> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>( new APIResponse<>(false,"Bad credentials", "Error Occurred" ), HttpStatus.BAD_REQUEST);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

       // String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getEmail());

       // ResponseCookie jwtToken = jwtUtils.generateJwtCookie(userDetails);

       // ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                roles,
                jwtToken);

        return ResponseEntity.ok(new APIResponse<>(true, "User Authenticated", response));
     //   return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse<String>> registerUser(@Valid @RequestBody SignupDto signUpDto) {
        if (userRepository.existsByUserName(signUpDto.getUsername())) {
            APIResponse<String> response = new APIResponse<>(false,"Username is already taken!","Error Occurred");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            APIResponse<String> response = new APIResponse<>(false,"Email is already in use!","Error Occurred");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        User user = new User(
                signUpDto.getUsername(),
                signUpDto.getName(),
                signUpDto.getEmail(),
                passwordEncoder.encode(signUpDto.getPassword())
        );

        Set<String> strRoles = signUpDto.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "seller":
                        Role modRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        APIResponse<String> response = new APIResponse<>(true,"User registered successfully!","success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/username")
    public ResponseEntity<String> getUsername(Authentication authentication) {
        String username;
        if(authentication != null){
            username = authentication.getName();
        }else{
            username = "null";
        }
        return ResponseEntity.ok(username);
    }


    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal() ;
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                roles
        );

        //  return ResponseEntity.ok(response);
        return ResponseEntity.ok(response);

    }


    @PostMapping("/forget-password")
    public ResponseEntity<APIResponse<String>> forgetPassword(@Valid @RequestBody EmailRequestDto emailRequestDto) {
        String response = passwordResetService.passwordReset(emailRequestDto);
        return ResponseEntity.ok(new APIResponse<>(true, response, "success"));
    }



    @PostMapping("/reset-password/{email}")
        public ResponseEntity<APIResponse<String>> resetPassword(@Valid @RequestBody ResetRequestDto resetDto, @PathVariable String email) {
        String response = passwordResetService.passwordResetByCode(resetDto,email);
        return ResponseEntity.ok(new APIResponse<>(true, response, "success"));
    }



//    @PostMapping("/sign-out")
//    public ResponseEntity<?> signOutUser(Authentication authentication) {
//    ResponseCookie cookie = jwtUtils.getCleanJwtCookies();
//    return ResponseEntity.ok()
//            .header(HttpHeaders.SET_COOKIE, cookie.toString())
//            .body( new MessageResponse("You have been signed out"));
//    }

}
