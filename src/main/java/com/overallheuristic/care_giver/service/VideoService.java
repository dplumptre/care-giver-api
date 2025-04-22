package com.overallheuristic.care_giver.service;

import com.overallheuristic.care_giver.dto.VideoDto;
import com.overallheuristic.care_giver.dto.payload.VideoRequestDto;
import com.overallheuristic.care_giver.repositories.VideoRepository;
import com.overallheuristic.care_giver.utils.enums.VideoType;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface VideoService {
    VideoDto create(VideoRequestDto videoDto);


    VideoDto update(@Valid VideoDto videoDto, Long id);

    String destroy(Long id);

    VideoDto getVideo(@Valid Long id);

    List<VideoDto> getVideos(Optional<VideoType> type);
}
