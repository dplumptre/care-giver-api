package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.utils.enums.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findByTitle(String title);

    List<Video> findByVideoType(VideoType videoType);
}
