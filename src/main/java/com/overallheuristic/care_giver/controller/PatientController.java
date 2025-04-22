package com.overallheuristic.care_giver.controller;


import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.PatientDto;
import com.overallheuristic.care_giver.dto.VideoDto;
import com.overallheuristic.care_giver.service.PatientService;
import com.overallheuristic.care_giver.utils.AuthUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patients")
public class PatientController {

    @Autowired
    PatientService patientService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<APIResponse<PatientDto>> create(@Valid @RequestBody PatientDto patientDto) {
        PatientDto savedPatientDto = this.patientService.createPatient(patientDto,this.authUtil.loggedInUser());
        return  new ResponseEntity<>(new APIResponse<>(true, "patient created successfully", savedPatientDto), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<APIResponse<List<PatientDto>>> getAllPatients() {
        List<PatientDto> savedPatientDto = this.patientService.getAll(this.authUtil.loggedInUser());
        return  ResponseEntity.ok(new APIResponse<>(true, "List of Patients", savedPatientDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<PatientDto>> getPatientById(@PathVariable("id")  Long id) {
        PatientDto patientDto = this.patientService.getPatient(id);
        return  ResponseEntity.ok(new APIResponse<>(true, "Patient Details", patientDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<PatientDto>> updatePatient(@Valid @RequestBody PatientDto patientDto, @PathVariable Long id) {
        PatientDto updatePatient = this.patientService.update(patientDto,id);
        return ResponseEntity.ok(new APIResponse<>(true,"Patient was updated successfully",updatePatient));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deletePatient(@PathVariable Long id) {
        return ResponseEntity.ok(new APIResponse<>(true,"Patient was deleted successfully",patientService.destroy(id)));
    }


}
