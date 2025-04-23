package com.overallheuristic.care_giver.controller;

import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.payload.HomeSetupStatusDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupStatusRequestDto;
import com.overallheuristic.care_giver.service.HomeSetupStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home-setup")
public class HomeSetupStatusController {

    @Autowired
    private HomeSetupStatusService homeSetupStatusService;

    @GetMapping("{patient_id}")
    public ResponseEntity<APIResponse<HomeSetupStatusDto>> homeSetupStatus(@PathVariable Long patient_id, @RequestBody HomeSetupStatusRequestDto homeSetupStatusRequestDto) {
      HomeSetupStatusDto homeStatus = homeSetupStatusService.getTaskStatus(patient_id, homeSetupStatusRequestDto);
      return ResponseEntity.ok(new APIResponse<>(true,"All task",homeStatus));
    }
}
