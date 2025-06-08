package com.overallheuristic.care_giver.command;

import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.repositories.VideoRepository;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class VideoCommand implements CommandLineRunner {

    @Autowired
    VideoRepository videoRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] titles = {
                "What is Stroke",
                "Caregiver Guide  Move Stroke Survivors Safely",
                "Light Stretching & Mobility: Neck rolls ",
                "Light Stretching & Mobility: Shoulder rolls ",
                "The Cat-Cow stretch",
                "Diaphragmatic breathing in sitting",
                "Ankle Circles",
                "Single leg balance -  eyes open"
        };

        String[] linkCodes = {
                "https://www.youtube.com/embed/No7eawZMmWo?rel=0&enablejsapi=1&playsinline=1&showinfo=0&controls=1&fullscreen=1",
                "https://www.youtube.com/embed/pVcSoDEIAB8?rel=0&enablejsapi=1&playsinline=1&showinfo=0&controls=1&fullscreen=1",
                "https://media.physitrack.com/exercises/5e313fe3-61a5-4abe-b572-0c1d00a301de/en/video_720p.mp4",
                "https://media.physitrack.com/exercises/99088d33-02e0-47a8-acb5-7d3bdac94d14/en/video_1280x720.mp4",
                "https://media.physitrack.com/exercises/02834c20-b334-43d5-a1d7-ba78d2b1cec4/en/video_1280x720.mp4",
                "https://media.physitrack.com/exercises/7e15b5f5-58e9-4e31-a5ad-e4f9a60f47d5/en/video_1280x720.mp4",
                "https://media.physitrack.com/exercises/d60d8c05-a0d4-4061-b3c1-1e040aaf2e9f/en/video_1280x720.mp4",
                "https://media.physitrack.com/exercises/2a866b91-2835-4deb-9490-4709d66f76e6/en/video_1280x720.mp4",
                "https://www.youtube.com/embed/embed/rjSfZNg-GiM?rel=0&enablejsapi=1&playsinline=1&showinfo=0&controls=1&fullscreen=1",
        };

        String[] des = {
                "This video will help you learn about stroke, including the different types and how they affect the brain. It's designed to equip you with a clear understanding of what a stroke is, so you can better support those in your care.",
                "This video guides you on how to safely assist stroke survivors with movement and mobility. You'll learn techniques to protect both yourself and the person in your care, reducing the risk of injury and promoting recovery",
                "This exercise helps caregivers release tension and improve flexibility in the neck and shoulders. Regular neck rolls can reduce stress and stiffness caused by long hours of caregiving. Take a moment to care for yourself, too. Repeat 8 times for 1 set",
                "Shoulder rolls are a simple but effective exercise to relieve tension and improve mobility in the shoulders and upper back. They’re especially helpful for caregivers who spend long hours assisting others. Take a few minutes to relax your muscles and reset your posture. 10 forward shoulder rolls and 10 backward shoulder rolls, Repeat 8 times for 1 set.",
                "The Cat-Cow stretch is a gentle yoga movement that helps relieve tension in the back and improve spinal flexibility. It’s ideal for caregivers who may experience back strain from daily tasks. Follow along to ease stiffness and support a healthier posture, Repeat 8 times for 1 set.",
                "Diaphragmatic breathing, also known as belly breathing, helps reduce stress and increase oxygen flow. This exercise can be done while sitting and is great for caregivers to stay calm and centered throughout the day. Learn how to breathe deeply and effectively to support both body and mind.",
                "This exercise helps improve circulation and mobility in the feet and ankles of stroke survivors. As a caregiver, gently support the patient’s leg and guide their foot in slow, circular motions. Encourage regular practice to reduce stiffness and support recovery. Repeat 10 times for 1 set.",
                "This exercise helps improve balance and stability in stroke survivors. Stand close by for support as the patient lifts one foot slightly off the ground while keeping their eyes open and focusing ahead. Always ensure their safety by being ready to assist or hold them steady if needed."
        };

        ActivityType[] videoTypes = {
                ActivityType.LEARNING_HUB,
                ActivityType.LEARNING_HUB,
                ActivityType.CARER_EXERCISE,
                ActivityType.CARER_EXERCISE,
                ActivityType.CARER_EXERCISE,
                ActivityType.PATIENT_EXERCISE_SUPPORT,
                ActivityType.PATIENT_EXERCISE_SUPPORT,
                ActivityType.PATIENT_EXERCISE_SUPPORT,
        };

        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String linkCode = linkCodes[i];
            ActivityType videoType = videoTypes[i];
            String description = des[i];

            Video myVee = videoRepository.findByTitle(title);
            if (myVee == null) {
                Video video = new Video();
                video.setTitle(title);
                video.setLink(linkCode);
                video.setVideoType(videoType);
                video.setDescription(description);
                videoRepository.save(video);
            }
        }
    }
}
