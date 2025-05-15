package com.overallheuristic.care_giver.command;

import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.repositories.VideoRepository;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VideoCommand implements CommandLineRunner {

    @Autowired
    VideoRepository videoRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] titles = {
                "What is Stroke",
                "Caregiver Guide  Move Stroke Survivors Safely",
                "Exercise For Stroke Survivors",
                "Self Care for Caregivers  5 Minute Routine",
        };

        String[] linkCodes = {
                "No7eawZMmWo",
                "pVcSoDEIAB8",
                "pxyh7fWi4rs",
                "eo6w7muVrmo"
        };

        ActivityType[] videoTypes = {
                ActivityType.LEARNING_HUB,
                ActivityType.LEARNING_HUB,
                ActivityType.PATIENT_EXERCISE_SUPPORT,
                ActivityType.CARER_EXERCISE
        };

        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String linkCode = linkCodes[i];
            ActivityType videoType = videoTypes[i];

            Video myVee = videoRepository.findByTitle(title);
            if (myVee == null) {
                Video video = new Video();
                video.setTitle(title);
                video.setLink(linkCode);
                video.setVideoType(videoType);
                video.setDescription("goal is to help them feel supported from head to toe");
                videoRepository.save(video);
            }
        }
    }
}
