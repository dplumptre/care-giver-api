package com.overallheuristic.care_giver.controller;

import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.MedicationDto;
import com.overallheuristic.care_giver.dto.MedicationResultDto;
import com.overallheuristic.care_giver.dto.payload.MedicationLogRequestDto;
import com.overallheuristic.care_giver.dto.payload.MedicationRequestDto;
import com.overallheuristic.care_giver.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<APIResponse<List<MedicationDto>>> getMedications(@PathVariable Long patientId) {
        List <MedicationDto> response = medicationService.getMedications(patientId);
        return new ResponseEntity<>(new APIResponse<>(true,"Medication List", response), HttpStatus.OK);
    }

    @GetMapping("/{medicationId}")
    public ResponseEntity<APIResponse<MedicationDto>> getMedication(@PathVariable Long medicationId) {
        MedicationDto response = medicationService.getMedication(medicationId);
        return new ResponseEntity<>(new APIResponse<>(true,"Medication", response), HttpStatus.OK);
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


    @PostMapping("add-medication-log")
    public ResponseEntity<APIResponse<String>> createMedicationLog(@RequestBody MedicationLogRequestDto request) {
        String response = medicationService.createMedicationLog(request);
        return new ResponseEntity<>(new APIResponse<>(true,"successfully created Log", response), HttpStatus.CREATED);
    }

    @GetMapping("/result/{patientId}")
    public ResponseEntity<APIResponse<MedicationResultDto>> getStreak(@PathVariable Long patientId) {
        MedicationResultDto result = medicationService.calculateStreakForPatient(patientId);
        return new ResponseEntity<>(new APIResponse<>(true,"successfully created Log", result), HttpStatus.OK);

    }

}
