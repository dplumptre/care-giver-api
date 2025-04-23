package com.overallheuristic.care_giver.controller;

import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.HomeSetupStatusDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupStatusRequestDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupUpdateRequest;
import com.overallheuristic.care_giver.service.HomeSetupStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home-setup")
public class HomeSetupStatusController {

    @Autowired
    private HomeSetupStatusService homeSetupStatusService;

    @GetMapping("{patient_id}")
    public ResponseEntity<APIResponse<List<HomeSetupStatusDto>>> homeSetupStatus(@PathVariable Long patient_id) {
      List<HomeSetupStatusDto> homeStatus = homeSetupStatusService.getTaskStatus(patient_id);
      return ResponseEntity.ok(new APIResponse<>(true,"All task",homeStatus));
    }

    @PutMapping("{patient_id}/update-task")
    public ResponseEntity<APIResponse<String>> updateHomeSetupStatus(
            @PathVariable Long patient_id,
            @RequestBody HomeSetupUpdateRequest request) {

        homeSetupStatusService.updateTaskStatus(patient_id, request);
        return ResponseEntity.ok(new APIResponse<>(true, "Task updated successfully", null));
    }

}
