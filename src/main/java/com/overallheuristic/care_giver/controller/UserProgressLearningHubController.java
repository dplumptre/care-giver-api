package com.overallheuristic.care_giver.controller;

import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.UserProgressLearningHubResultDto;
import com.overallheuristic.care_giver.dto.UserProgressLearningHubDto;
import com.overallheuristic.care_giver.dto.payload.UserProgressRequestDto;
import com.overallheuristic.care_giver.service.UserProgressLearningHubService;
import com.overallheuristic.care_giver.utils.AuthUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/learning-hub-progress")
public class UserProgressLearningHubController{

    @Autowired
    private UserProgressLearningHubService userProgressLearningHubService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("user")
    public ResponseEntity<APIResponse<UserProgressLearningHubDto>> createUserProgressLearningHub(@Valid @RequestBody UserProgressRequestDto userProgressLearningHubDto) {
        UserProgressLearningHubDto response =userProgressLearningHubService.create(userProgressLearningHubDto);
        return new ResponseEntity<>(new APIResponse<>(true,"learning hub result created successfully",response), HttpStatus.CREATED);
    }

    @GetMapping("user")
    public ResponseEntity<APIResponse<List<UserProgressLearningHubDto>>> getUserProgressLearningHubs() {
        List<UserProgressLearningHubDto> userProgressLearningHubDtos = userProgressLearningHubService.getUsersProgress(authUtil.loggedInUser(),true);
        return ResponseEntity.ok(new APIResponse<>(true,"learning hub progress",userProgressLearningHubDtos));
    }

    @GetMapping("user/result")
    public ResponseEntity<APIResponse<UserProgressLearningHubResultDto>> getUserProgressLearningHubResult(){
        UserProgressLearningHubResultDto result = userProgressLearningHubService.getUsersProgressResult(authUtil.loggedInUser(),true);
        return ResponseEntity.ok(new APIResponse<>(true,"learning hub result",result));
    }

    @GetMapping("user/learner-hub-quiz-status/{videoId}")
    public ResponseEntity<APIResponse<Boolean>> getUserLearningHubQuizStatus(@PathVariable Long videoId){
        Boolean result = userProgressLearningHubService.getUserLearningHubQuizStatus(authUtil.loggedInUser(),videoId);
        return ResponseEntity.ok(new APIResponse<>(true,"Quiz Status",result));
    }

}
