package com.overallheuristic.care_giver.controller;

import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.payload.MedicationRequestDto;
import com.overallheuristic.care_giver.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;


    @PostMapping
    public ResponseEntity<APIResponse<String>> createMedication(@RequestBody MedicationRequestDto request) {
        String response = medicationService.createMedicationWithDosageTimes(request);
        return new ResponseEntity<>(new APIResponse<>(true,"successfully created", response), HttpStatus.CREATED);
    }
}
