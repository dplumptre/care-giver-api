package com.overallheuristic.care_giver.controller;


import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.ExerciseResultDto;
import com.overallheuristic.care_giver.dto.ExerciseSessionDto;
import com.overallheuristic.care_giver.dto.payload.ExerciseSessionRequestDto;
import com.overallheuristic.care_giver.model.ExerciseSession;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.service.ExerciseSessionService;
import com.overallheuristic.care_giver.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exercise-session")
public class ExerciseSessionController {

    @Autowired
    private ExerciseSessionService exerciseSessionService;
    @Autowired
    private  AuthUtil authUtil;

    @PostMapping("complete")
    ResponseEntity<APIResponse<String>> completeExerciseSession(
            @RequestBody ExerciseSessionRequestDto exerciseSessionDto,
            @RequestParam(required = false) Long patientId) {
        User user = authUtil.loggedInUser();
        String savedExercise = exerciseSessionService.completeSession(exerciseSessionDto,patientId,user);

        return new ResponseEntity<>( new APIResponse<>(true, "Exercise Completed!", savedExercise), HttpStatus.CREATED);
    }


    @GetMapping("result")
    ResponseEntity<APIResponse<ExerciseResultDto>> getResult(){
        User user = authUtil.loggedInUser();
       ExerciseResultDto resultDto =  exerciseSessionService.getExerciseResult(user);
       return new ResponseEntity<>(new APIResponse<>(true, "Exercise Result!", resultDto), HttpStatus.OK);
    }



}
