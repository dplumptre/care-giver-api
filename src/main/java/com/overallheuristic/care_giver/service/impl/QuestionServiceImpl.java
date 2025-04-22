package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.QuestionDto;
import com.overallheuristic.care_giver.dto.VideoDto;
import com.overallheuristic.care_giver.dto.payload.QuestionRequestDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.Question;
import com.overallheuristic.care_giver.model.Video;
import com.overallheuristic.care_giver.repositories.QuestionRepository;
import com.overallheuristic.care_giver.repositories.VideoRepository;
import com.overallheuristic.care_giver.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final ModelMapper modelMapper;
    private final VideoRepository videoRepository;
    private QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, ModelMapper modelMapper, VideoRepository videoRepository) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
        this.videoRepository = videoRepository;
    }

    @Override
    public QuestionDto create(QuestionRequestDto questionRequestDto) {

        Video video = videoRepository.findById(questionRequestDto.getVideoId()).orElseThrow(() -> new APIException("Video not found"));
        Question question = new Question();
        question.setQuestion(questionRequestDto.getQuestion());
        question.setVideo(video);
        Question savedQuestion = questionRepository.save(question);
        return modelMapper.map( savedQuestion ,QuestionDto.class);
    }

    @Override
    public List<QuestionDto> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> modelMapper.map(question,QuestionDto.class)).toList();
    }

    @Override
    public QuestionDto updateQuestion(Long id, QuestionRequestDto questionDto) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new APIException("Question not found"));
        question.setQuestion(questionDto.getQuestion());
        Question savedQuestion = questionRepository.save(question);
        return modelMapper.map( savedQuestion ,QuestionDto.class);
    }

    @Override
    public String deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new APIException("Question not found"));
        questionRepository.delete(question);
        return "Question successfully deleted";
    }

    @Override
    public List<QuestionDto> getQuestionsByVideo(Long videoId) {
           Video video = videoRepository.findById(videoId).orElseThrow(() -> new APIException("Video not found"));
           List<Question> questions = questionRepository.findByVideo(video);
           if (questions == null) {
               throw new APIException("There are no questions in the"+ video.getTitle() + " video");
           }
           return questions.stream().map(question -> modelMapper.map(question,QuestionDto.class)).toList();
    }
}
