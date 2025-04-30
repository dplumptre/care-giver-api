package com.overallheuristic.care_giver.controller;

import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.payload.MedicationRequestDto;
import com.overallheuristic.care_giver.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



    @DeleteMapping("/{medicationId}")
    public ResponseEntity<APIResponse<String>> deleteMedication(@PathVariable Long medicationId) {
        String response = medicationService.deleteMedicationWithDosageTimes(medicationId);
        return new ResponseEntity<>(new APIResponse<>(true,"successfully Deleted", response), HttpStatus.OK);
    }


    @PutMapping("/{medicationId}")
    public ResponseEntity<APIResponse<String>> updateMedication(@PathVariable Long medicationId, @RequestBody MedicationRequestDto request) {
        String response = medicationService.updateMedicationWithDosageTimes(medicationId, request);
        return new ResponseEntity<>(new APIResponse<>(true,"successfully Updated", response), HttpStatus.OK);
    }

}
