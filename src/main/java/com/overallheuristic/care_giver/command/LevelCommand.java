package com.overallheuristic.care_giver.command;

import com.overallheuristic.care_giver.model.Level;
import com.overallheuristic.care_giver.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LevelCommand implements CommandLineRunner {

    @Autowired
    private LevelRepository levelRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] levelNames = {
                "Bronze", "Silver", "Gold", "Platinum", "Diamond",
                "Emerald", "Ruby", "Sapphire", "Master", "Champion"
        };

        for (int i = 0; i < levelNames.length; i++) {
            String levelName = levelNames[i];

            // Check if level with the same name exists
            if (levelRepository.findByLevelName(levelName).isEmpty()) {
                Level level = new Level();
                level.setLevelName(levelName);
                level.setLevelNumber(i + 1);
                level.setScore(100);
                levelRepository.save(level);
            }
        }
    }
}
