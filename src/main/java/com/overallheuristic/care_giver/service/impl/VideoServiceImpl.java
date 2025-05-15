package com.overallheuristic.care_giver.service.impl;


import com.overallheuristic.care_giver.dto.VideoDto;
import com.overallheuristic.care_giver.dto.payload.VideoRequestDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.repositories.VideoRepository;
import com.overallheuristic.care_giver.service.VideoService;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {


    private VideoRepository videoRepository;
    private ModelMapper modelMapper;

    public VideoServiceImpl(VideoRepository videoRepository, ModelMapper modelMapper) {
        this.videoRepository = videoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VideoDto create(VideoRequestDto videoDto) {
        Video video = modelMapper.map(videoDto, Video.class);
        Video existVideo =videoRepository.findByTitle(videoDto.getTitle());
        if(existVideo != null) {
            throw new APIException("Title already exists");
        }
        Video savedVideo = videoRepository.save(video);
        return modelMapper.map(savedVideo, VideoDto.class);
    }



    public List<VideoDto> getVideos(Optional<ActivityType> videoType) {
        List<Video> videos;

        if (videoType.isPresent()) {
            videos = videoRepository.findByVideoType(videoType.get());
        } else {
            videos = videoRepository.findAll();
        }

        return videos.stream()
                .map(v -> modelMapper.map(v, VideoDto.class))
                .toList();
    }






    @Override
    public VideoDto update(VideoDto videoDto, Long id) {

       Video video = videoRepository.findById(id).orElseThrow( ()-> new APIException("Video not found"));
       Video payload = modelMapper.map(videoDto, Video.class);
       video.setTitle(payload.getTitle());
       video.setDescription(payload.getDescription());
       video.setLink(payload.getLink());
       videoRepository.save(video);
       return modelMapper.map(video, VideoDto.class);
    }

    @Override
    public String destroy(Long id) {
        Video video = videoRepository.findById(id).orElseThrow( ()-> new APIException("Video not found"));
        videoRepository.delete(video);
        return "Video deleted";
    }

    @Override
    public VideoDto getVideo(Long id) {
        Video video = videoRepository.findById(id).orElseThrow( ()-> new APIException("Video not found"));
        return modelMapper.map(video, VideoDto.class);
    }


}
