
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
import org.springframework.stereotype.Component;

import java.util.*;

@Component
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
        // Map question text to activity type
        Map<String, ActivityType> questions = new LinkedHashMap<>();

        questions.put("What happens during a stroke", ActivityType.LEARNING_HUB);
        questions.put("Which type of stroke is caused by a blockage, like a blood clot, stopping blood from reaching the brain", ActivityType.LEARNING_HUB);
        questions.put("In the FAST acronym for spotting stroke signs, what does the 'S' stand for", ActivityType.LEARNING_HUB);
        questions.put("Which of the following is a risk factor for stroke", ActivityType.LEARNING_HUB);
        questions.put("Why is a stroke considered a medical emergency", ActivityType.LEARNING_HUB);
        questions.put("What is the first step before transferring a stroke patient from bed to wheelchair", ActivityType.LEARNING_HUB);
        questions.put("When helping a stroke patient sit at the edge of the bed, what is important to do", ActivityType.LEARNING_HUB);
        questions.put("Where should the wheelchair be placed during a bed-to-chair transfer", ActivityType.LEARNING_HUB);
        questions.put("What should caregivers avoid doing when transferring a stroke patient", ActivityType.LEARNING_HUB);
        questions.put("Why is proper positioning in bed important for stroke patients", ActivityType.LEARNING_HUB);
        questions.put("What is the caregiver's primary role when supporting a stroke survivor with walking", ActivityType.PATIENT_EXERCISE_SUPPORT);
        questions.put("Which of these is a recommended warm-up exercise before standing or walking", ActivityType.PATIENT_EXERCISE_SUPPORT);
        questions.put("When helping a stroke survivor stand up, where should the caregiver provide support", ActivityType.PATIENT_EXERCISE_SUPPORT);
        questions.put("How should you guide a stroke survivor during assisted walking", ActivityType.PATIENT_EXERCISE_SUPPORT);
        questions.put("What is a good way to help a stroke survivor cool down after walking practice", ActivityType.PATIENT_EXERCISE_SUPPORT);
        questions.put("Why is deep breathing an important part of the caregiver stress relief routine", ActivityType.CARER_EXERCISE);
        questions.put("Which of the following is a technique used to release neck and shoulder tension", ActivityType.CARER_EXERCISE);
        questions.put("What is the purpose of the overhead reach and side stretch in the routine", ActivityType.CARER_EXERCISE);
        questions.put("How can caregivers gently activate their lower body during this routine", ActivityType.CARER_EXERCISE);
        questions.put("What is the final affirmation at the end of the routine meant to do", ActivityType.CARER_EXERCISE);

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

        optionsData.put("Why is proper positioning in bed important for stroke patients", List.of(
                new AnswerOptionData("To make the bed look neat", false),
                new AnswerOptionData("To improve circulation and prevent pressure sores", true),
                new AnswerOptionData("So they can sleep longer", false),
                new AnswerOptionData("To reduce medication use", false)
        ));

        for (Map.Entry<String, ActivityType> entry : questions.entrySet()) {
            String questionText = entry.getKey();
            ActivityType activityType = entry.getValue();

            Optional<Question> existingQuestion = questionRepository.findByQuestion(questionText);


            Question question;


            if (existingQuestion.isPresent()) {
                Question q = existingQuestion.get();
                log.info("Found existing question in DB: id={} text=\"{}\"", q.getId(), q.getQuestion());
            } else {
                Optional<Video> optionalVideo = videoRepository.findFirstByVideoType(activityType);
                if (optionalVideo.isEmpty()) {
                    log.info("No video found for activity type: " + activityType);
                    continue;
                }

                question = new Question();
                question.setQuestion(questionText);
                question.setVideo(optionalVideo.get());

                try {
                    question = questionRepository.save(question);
                    log.info("Created question: " + questionText);
                } catch (Exception e) {
                    log.info("Error saving question: " + questionText);
                    continue; // skip to next question
                }


            // Only create options for LEARNING_HUB questions
            if (activityType == ActivityType.LEARNING_HUB) {
                List<AnswerOptionData> options = optionsData.get(questionText);
                if (options == null) {
                    log.info("No options provided for LEARNING_HUB question: " + questionText);
                    continue;
                }

                for (AnswerOptionData opt : options) {
                    try {
                        boolean exists = answerOptionRepository.existsByQuestionAndAnswerOption(question, opt.text());
                        if (exists) {
                            log.info("Skipping duplicate option: " + opt.text());
                            continue;
                        }

                        AnswerOption answerOption = new AnswerOption();
                        answerOption.setQuestion(question);
                        answerOption.setAnswerOption(opt.text());
                        answerOption.setIsCorrect(opt.isCorrect());

                        answerOptionRepository.save(answerOption);
                        log.info("Saved option: " + opt.text());
                    } catch (Exception e) {
                        log.info("Error saving option: " + opt.text() + " for question: " + questionText);

                    }
                }
            }
        }
        }
    }

    private record AnswerOptionData(String text, boolean isCorrect) {}
}
