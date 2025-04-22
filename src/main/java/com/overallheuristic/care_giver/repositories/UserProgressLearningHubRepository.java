package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.model.UserProgressLearningHub;
import com.overallheuristic.care_giver.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProgressLearningHubRepository extends JpaRepository <UserProgressLearningHub, Long> {

    List<UserProgressLearningHub> findByUserAndIsSuccessful(User user, boolean isSuccessful);

    UserProgressLearningHub findByUserAndIsSuccessfulAndVideo(User user, boolean b, Video video);
}
