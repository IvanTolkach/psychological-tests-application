package dev.tolkach.attemptsservice.adapter.out.persistence.localstorage;

import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptScoreRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "repository.type", havingValue = "local")
public class LocalStorageTestAttemptScoreAdapter implements TestAttemptScoreRepository {

    private final Map<UUID, TestAttemptScore> storage = new ConcurrentHashMap<>();

    @Override
    public TestAttemptScore save(TestAttemptScore testAttemptScore) {
        if (testAttemptScore.getId() == null) {
            testAttemptScore.setId(UUID.randomUUID());
        }
        storage.put(testAttemptScore.getId(), testAttemptScore);
        System.out.println("Saved into local storage.");
        return testAttemptScore;
    }

    @Override
    public List<TestAttemptScore> saveAll(List<TestAttemptScore> scores) {

        List<TestAttemptScore> saved = new ArrayList<>();

        for (TestAttemptScore score : scores) {

            if (score.getId() == null) {
                score.setId(UUID.randomUUID());
            }

            storage.put(score.getId(), score);
            saved.add(score);
        }

        System.out.println("Saved into local storage: " + saved.size());

        return saved;
    }

    @Override
    public Optional<TestAttemptScore> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<TestAttemptScore> findByFilter(TestAttemptScore filter) {
        return storage.values().stream()
                .filter(score -> (filter.getId() == null || filter.getId().equals(score.getId())) &&
                        (filter.getTestAttemptId() == null || filter.getTestAttemptId().equals(score.getTestAttemptId())) &&
                        (filter.getScaleId() == null || filter.getScaleId().equals(score.getScaleId())) &&
                        (filter.getScore() == null || filter.getScore().equals(score.getScore())) &&
                        (filter.getInterpretation() == null || filter.getInterpretation().equalsIgnoreCase(score.getInterpretation())))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}
