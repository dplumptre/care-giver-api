package com.overallheuristic.care_giver.service;


import com.overallheuristic.care_giver.dto.ResultDto;

public interface ResultService {
    ResultDto getResults(Long userId);
}
