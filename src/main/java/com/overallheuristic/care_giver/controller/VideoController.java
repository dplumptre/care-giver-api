package com.overallheuristic.care_giver.controller;

import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.VideoDto;
import com.overallheuristic.care_giver.dto.payload.VideoRequestDto;
import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.service.VideoService;
import com.overallheuristic.care_giver.utils.enums.VideoType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping
    public ResponseEntity<APIResponse<VideoDto>> addVideo(@Valid  @RequestBody VideoRequestDto videoDto) {
        VideoDto savedVideo = videoService.create(videoDto);
        APIResponse<VideoDto> apiResponse = new APIResponse<>(true, "video was created successfully",savedVideo);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<APIResponse<List<VideoDto>>> listVideos(
            @RequestParam(value = "type", required = false) VideoType type
    ) {
        List<VideoDto> videosDto = videoService.getVideos(Optional.ofNullable(type));
        return ResponseEntity.ok(new APIResponse<>(true, "success", videosDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<VideoDto>> getVideoById(@PathVariable("id")  Long id) {
        VideoDto videoDto = videoService.getVideo(id);
        return ResponseEntity.ok(new APIResponse<>(true,"video details",videoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<VideoDto>> updateVideo(@Valid @RequestBody VideoDto videoDto, @PathVariable Long id) {
        VideoDto videosDto = videoService.update(videoDto,id);
        return ResponseEntity.ok(new APIResponse<>(true,"video was updated successfully",videosDto));
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteVideo(@PathVariable Long id) {
        return ResponseEntity.ok(new APIResponse<>(true,"video was deleted successfully",videoService.destroy(id)));
   }




}
