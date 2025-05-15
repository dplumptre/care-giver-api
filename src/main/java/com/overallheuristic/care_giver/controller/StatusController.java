package com.overallheuristic.care_giver.controller;


import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.QuestionDto;
import com.overallheuristic.care_giver.dto.ResultDto;
import com.overallheuristic.care_giver.service.ResultService;
import com.overallheuristic.care_giver.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/status/result")
public class StatusController {

    @Autowired
    private ResultService resultService;
    @Autowired
    AuthUtil authUtil;



    @GetMapping
    public ResponseEntity<APIResponse<ResultDto>> getResults() {
        ResultDto resultDto = resultService.getResults(authUtil.loggedInUser().getUserId());
        return ResponseEntity.ok(new APIResponse<>(true, "Dashboard Results", resultDto));
    }
}
