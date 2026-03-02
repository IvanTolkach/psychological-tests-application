package dev.tolkach.testsservice.adapter.out.persistence.repository;

import dev.tolkach.testsservice.adapter.out.persistence.entity.AnswerOptionEntity;
import dev.tolkach.testsservice.adapter.out.persistence.mapper.AnswerOptionMapper;
import dev.tolkach.testsservice.adapter.out.persistence.specification.AnswerOptionSpecification;
import dev.tolkach.testsservice.application.model.AnswerOption;
import dev.tolkach.testsservice.application.port.out.AnswerOptionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AnswerOptionRepositoryAdapter implements AnswerOptionRepository {

    private final JpaAnswerOptionRepository jpaAnswerOptionRepository;
    private final AnswerOptionMapper answerOptionMapper;

    public AnswerOptionRepositoryAdapter(JpaAnswerOptionRepository jpaAnswerOptionRepository, AnswerOptionMapper answerOptionMapper) {
        this.jpaAnswerOptionRepository = jpaAnswerOptionRepository;
        this.answerOptionMapper = answerOptionMapper;
    }

    @Override
    public AnswerOption save(AnswerOption answerOption) {
        AnswerOptionEntity entity = answerOptionMapper.toEntity(answerOption);
        AnswerOptionEntity saved = jpaAnswerOptionRepository.save(entity);
        return answerOptionMapper.toDomain(saved);
    }

    @Override
    public Optional<AnswerOption> findById(UUID id) {
        return jpaAnswerOptionRepository.findById(id)
                .map(answerOptionMapper::toDomain);
    }

    @Override
    public List<AnswerOption> findByFilter(AnswerOption filter) {
        return jpaAnswerOptionRepository.findAll(AnswerOptionSpecification.filterBy(filter)).stream()
                .map(answerOptionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaAnswerOptionRepository.deleteById(id);
    }
}