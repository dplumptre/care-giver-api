
package com.overallheuristic.care_giver.command;

import com.overallheuristic.care_giver.model.AnswerOption;
import com.overallheuristic.care_giver.model.Question;
import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.repositories.AnswerOptionRepository;
import com.overallheuristic.care_giver.repositories.QuestionRepository;
import com.overallheuristic.care_giver.repositories.VideoRepository;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Order(6)
public class QuestionCommand implements CommandLineRunner {

    private final VideoRepository videoRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    @Autowired
    public QuestionCommand(VideoRepository videoRepository, QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository) {
        this.videoRepository = videoRepository;
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
    }


    private static final Logger log = LoggerFactory.getLogger(QuestionCommand.class);

    @Override
    public void run(String... args) throws Exception {
        Optional<Video> firstVideo = videoRepository.findFirstByTitle("What is Stroke");
        Optional<Video> secondVideo = videoRepository.findFirstByTitle("Caregiver Guide  Move Stroke Survivors Safely"); // Change if different type

        if (firstVideo.isEmpty() || secondVideo.isEmpty()) {
            log.info("Missing required videos");
            return;
        }

        List<QuestionSeed> seeds = List.of(
                new QuestionSeed("What happens during a stroke", firstVideo.get()),
                new QuestionSeed("Which type of stroke is caused by a blockage, like a blood clot, stopping blood from reaching the brain", firstVideo.get()),
                new QuestionSeed("In the FAST acronym for spotting stroke signs, what does the 'S' stand for", firstVideo.get()),
                new QuestionSeed("Which of the following is a risk factor for stroke", firstVideo.get()),
                new QuestionSeed("What is the first step before transferring a stroke patient from bed to wheelchair", secondVideo.get()),
                new QuestionSeed("When helping a stroke patient sit at the edge of the bed, what is important to do", secondVideo.get()),
                new QuestionSeed("Where should the wheelchair be placed during a bed-to-chair transfer", secondVideo.get()),
                new QuestionSeed("What should caregivers avoid doing when transferring a stroke patient", secondVideo.get())
        );

        // Map question text to answer options
        Map<String, List<AnswerOptionData>> optionsData = new LinkedHashMap<>();

        optionsData.put("What happens during a stroke", List.of(
                new AnswerOptionData("It usually causes a heart attack", false),
                new AnswerOptionData("Brain cells start dying slowly over several days", false),
                new AnswerOptionData("It can cause dizziness that lasts for weeks", false),
                new AnswerOptionData("Brain cells start dying quickly when they don't get oxygen", true)
        ));

        optionsData.put("Which type of stroke is caused by a blockage, like a blood clot, stopping blood from reaching the brain", List.of(
                new AnswerOptionData("Hemorrhagic stroke", false),
                new AnswerOptionData("Ischemic stroke", true),
                new AnswerOptionData("Cardiac stroke", false),
                new AnswerOptionData("Transient stroke", false)
        ));

        optionsData.put("In the FAST acronym for spotting stroke signs, what does the 'S' stand for", List.of(
                new AnswerOptionData("Strength", false),
                new AnswerOptionData("Slouching", false),
                new AnswerOptionData("Speed", false),
                new AnswerOptionData("Speech", true)
        ));

        optionsData.put("Which of the following is a risk factor for stroke", List.of(
                new AnswerOptionData("Eating vegetables", false),
                new AnswerOptionData("High blood pressure", true),
                new AnswerOptionData("Regular physical activity", false),
                new AnswerOptionData("Staying hydrated", false)
        ));

        optionsData.put("What is the first step before transferring a stroke patient from bed to wheelchair", List.of(
                new AnswerOptionData("Grab the patient’s arms and pull them up", false),
                new AnswerOptionData("Ensure the bed is raised as high as possible", false),
                new AnswerOptionData("Explain what you're about to do and check for dizziness or weakness", true),
                new AnswerOptionData("Lift the patient quickly before they resist", false)
        ));

        optionsData.put("When helping a stroke patient sit at the edge of the bed, what is important to do", List.of(
                new AnswerOptionData("Pull them up by the arms", false),
                new AnswerOptionData("Let them move on their own", false),
                new AnswerOptionData("Use a rocking motion and support their weak side", true),
                new AnswerOptionData("Twist their body quickly to the side", false)
        ));

        optionsData.put("Where should the wheelchair be placed during a bed-to-chair transfer", List.of(
                new AnswerOptionData("Directly in front of the patient", false),
                new AnswerOptionData("On the patient’s weak side", false),
                new AnswerOptionData("At the head of the bed", false),
                new AnswerOptionData("Close to the bed on the stronger side", true)
        ));

        optionsData.put("What should caregivers avoid doing when transferring a stroke patient", List.of(
                new AnswerOptionData("Communicating clearly with the patient", false),
                new AnswerOptionData("Rushing the transfer", true),
                new AnswerOptionData("Using proper body mechanics", false),
                new AnswerOptionData("Making sure brakes are locked", false)
        ));



        for (QuestionSeed seed : seeds) {
            String questionText = seed.text();
            Video video = seed.video();

            Question question = questionRepository.findByQuestion(questionText)
                    .orElseGet(() -> {
                        Question q = new Question();
                        q.setQuestion(questionText);
                        q.setVideo(video);
                        return questionRepository.save(q);
                    });

            List<AnswerOptionData> options = optionsData.get(questionText);
            if (options == null) {
                log.info("No answer options for: " + questionText);
                continue;
            }

            for (AnswerOptionData opt : options) {
                if (answerOptionRepository.existsByQuestionAndAnswerOption(question, opt.text())) {
                    log.info("Skipping existing option: " + opt.text());
                    continue;
                }
                AnswerOption answerOption = new AnswerOption();
                answerOption.setQuestion(question);
                answerOption.setAnswerOption(opt.text());
                answerOption.setIsCorrect(opt.isCorrect());
                answerOptionRepository.save(answerOption);
            }

            log.info("Saved question + options: {}", questionText);
        }
    }
    private record QuestionSeed(String text, Video video) {}
    private record AnswerOptionData(String text, boolean isCorrect) {}
}
