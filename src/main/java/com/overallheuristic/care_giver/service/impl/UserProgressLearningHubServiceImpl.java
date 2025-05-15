package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.UserProgressLearningHubResultDto;
import com.overallheuristic.care_giver.dto.UserProgressLearningHubDto;
import com.overallheuristic.care_giver.dto.payload.UserProgressRequestDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.Level;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.model.UserProgressLearningHub;
import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.repositories.LevelRepository;
import com.overallheuristic.care_giver.repositories.UserProgressLearningHubRepository;
import com.overallheuristic.care_giver.repositories.VideoRepository;
import com.overallheuristic.care_giver.service.UserProgressLearningHubService;
import com.overallheuristic.care_giver.utils.AuthUtil;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProgressLearningHubServiceImpl implements UserProgressLearningHubService {

    @Autowired
    private UserProgressLearningHubRepository userProgressLearningHubRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LevelRepository levelRepository;

    @Override
    public UserProgressLearningHubDto create(UserProgressRequestDto userProgressRequestDto) {

         Video video = videoRepository.findById(userProgressRequestDto.getVideoId()).orElseThrow(()-> new  APIException("Video not found"));

        UserProgressLearningHub checkProgess = userProgressLearningHubRepository.findByUserAndIsSuccessfulAndVideo(authUtil.loggedInUser(), true,video);
        if (checkProgess != null) {
            throw new APIException("score for "+checkProgess.getVideo().getTitle()+ " already exists");
        }

         UserProgressLearningHub learningHub = new UserProgressLearningHub();
         learningHub.setUser(authUtil.loggedInUser());
         learningHub.setVideo(video);
         learningHub.setIsSuccessful(userProgressRequestDto.getIsSuccessful());
         learningHub.setScore(userProgressRequestDto.getScore());
         return modelMapper.map(userProgressLearningHubRepository.save(learningHub), UserProgressLearningHubDto.class);
    }

    @Override
    public List<UserProgressLearningHubDto> getUsersProgress(User user, boolean isSuccessful) {
        List<UserProgressLearningHub> userProgress = userProgressLearningHubRepository.findByUserAndIsSuccessful(user,isSuccessful);
        if(userProgress.isEmpty()){
            throw new APIException("There is no current progress ");
        }
        return userProgress.stream().map( response -> modelMapper.map(response, UserProgressLearningHubDto.class)).toList();
    }

    @Override
    public UserProgressLearningHubResultDto getUsersProgressResult(User user, boolean isSuccessful) {
        Integer currentLevel =  userProgressLearningHubRepository.findByUserAndIsSuccessful(user, isSuccessful).size();
        Integer level = videoRepository.findByVideoType(ActivityType.LEARNING_HUB).size();

        String levelName;
        if (currentLevel == 0) {
            levelName = "Beginner";
        } else {
            Level levelDetails = levelRepository.findLevelByLevelNumber(currentLevel);
            levelName = levelDetails.getLevelName();
        }
        return new UserProgressLearningHubResultDto( currentLevel, level, levelName);

        }

    @Override
    public Boolean getUserLearningHubQuizStatus(User user, Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(()-> new  APIException("Video not found"));

        UserProgressLearningHub checkProgess = userProgressLearningHubRepository.findByUserAndIsSuccessfulAndVideo(authUtil.loggedInUser(), true,video);

        return checkProgess != null;

    }


}
