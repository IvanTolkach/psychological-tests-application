package dev.tolkach.testsservice.adapter.out.persistence.repository;

import dev.tolkach.testsservice.adapter.out.persistence.entity.QuestionEntity;
import dev.tolkach.testsservice.adapter.out.persistence.mapper.QuestionMapper;
import dev.tolkach.testsservice.adapter.out.persistence.specification.QuestionSpecification;
import dev.tolkach.testsservice.application.model.Question;
import dev.tolkach.testsservice.application.port.out.QuestionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class QuestionRepositoryAdapter implements QuestionRepository {

    private final JpaQuestionRepository jpaQuestionRepository;
    public final QuestionMapper questionMapper;

    public QuestionRepositoryAdapter(JpaQuestionRepository jpaQuestionRepository, QuestionMapper questionMapper) {
        this.jpaQuestionRepository = jpaQuestionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public Question save(Question question) {
        QuestionEntity entity = questionMapper.toEntity(question);
        QuestionEntity saved = jpaQuestionRepository.save(entity);
        return questionMapper.toDomain(saved);
    }

    @Override
    public Optional<Question> findById(UUID id) {
        return jpaQuestionRepository.findById(id)
                .map(questionMapper::toDomain);
    }

    @Override
    public List<Question> findByFilter(Question filter) {
        return jpaQuestionRepository.findAll(QuestionSpecification.filterBy(filter)).stream()
                .map(questionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaQuestionRepository.deleteById(id);
    }
}