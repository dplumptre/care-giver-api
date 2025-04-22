package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.PatientDto;
import com.overallheuristic.care_giver.dto.VideoDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.repositories.PatientRepository;
import com.overallheuristic.care_giver.service.PatientService;
import com.overallheuristic.care_giver.utils.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientDto createPatient(PatientDto patientDto, User user) {

        Patient patient = patientRepository.findByNameOrPhone(patientDto.getName(),patientDto.getName());

        if(patient != null) {
            throw new APIException("Patient already exists");
        }

        Patient newPatient = new Patient();
        newPatient.setName(patientDto.getName());
        newPatient.setPhone(patientDto.getPhone());
        newPatient.setAddress(patientDto.getAddress());
        newPatient.setAffectedSide(patientDto.getAffectedSide());
        newPatient.setUser(user);
        Patient savedPatient = patientRepository.save(newPatient);
        return  modelMapper.map(savedPatient, PatientDto.class);



    }

    @Override
    public List<PatientDto> getAll(User user) {
        return patientRepository.findAllByUser(user).stream().map( pat -> modelMapper.map(pat, PatientDto.class)).toList();

    }
    
    @Override
    public PatientDto getPatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow( ()-> new APIException("Patient not found"));
        return modelMapper.map(patient, PatientDto.class);
    }



    @Override
    public PatientDto update(PatientDto patientDto, Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow( ()-> new APIException("Patient not found"));
        Patient payload = modelMapper.map(patientDto, Patient.class);
        patient.setName(patientDto.getName());
        patient.setAddress(patientDto.getAddress());
        patient.setAffectedSide(patientDto.getAffectedSide());
        patient.setPhone(patientDto.getPhone());
        patientRepository.save(patient);
        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    public String destroy(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow( ()-> new APIException("Patient not found"));
        patientRepository.delete(patient);
        return "Patient deleted";
    }

}
