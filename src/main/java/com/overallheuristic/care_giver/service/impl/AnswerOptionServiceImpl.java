package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.AnswerOptionDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.AnswerOption;
import com.overallheuristic.care_giver.model.Question;
import com.overallheuristic.care_giver.repositories.AnswerOptionRepository;
import com.overallheuristic.care_giver.repositories.QuestionRepository;
import com.overallheuristic.care_giver.service.AnswerOptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerOptionServiceImpl implements AnswerOptionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Value("${option.answers}")
    Integer answerOptionCount;


    @Override
    public AnswerOptionDto addAnswer(Long questionId, AnswerOptionDto answerOptionDto) {

        Question question = questionRepository.findById(questionId).orElseThrow( ()-> new RuntimeException("Question not found"));

        List<AnswerOption> answerOptionList = answerOptionRepository.findByQuestionId(questionId);

        if (answerOptionList.size() == answerOptionCount) {
            throw new APIException("The answer options are limited to "+answerOptionCount);
        }

        long correctCount = answerOptionList.stream()
                .filter(AnswerOption::getIsCorrect)
                .count();

        long incorrectCount = answerOptionList.size() - correctCount;

        if (incorrectCount == answerOptionCount-1 && !answerOptionDto.getIsCorrect()) {
            throw new APIException("You already have 3 incorrect options. The last option must be correct.");
        }

        if (correctCount == 1 && answerOptionDto.getIsCorrect()) {
            throw new APIException("Only one answer option can be correct.");
        }

        AnswerOption answerOption = new AnswerOption();
        answerOption.setAnswerOption(answerOptionDto.getAnswerOption());
        answerOption.setIsCorrect(answerOptionDto.getIsCorrect());
        answerOption.setQuestion(question);
        answerOptionRepository.save(answerOption);
        return modelMapper.map(answerOption, AnswerOptionDto.class);

    }
}
