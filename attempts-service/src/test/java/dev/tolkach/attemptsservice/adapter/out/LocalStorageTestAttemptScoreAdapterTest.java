package dev.tolkach.attemptsservice.adapter.out;

import dev.tolkach.attemptsservice.adapter.out.persistence.localstorage.LocalStorageTestAttemptScoreAdapter;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocalStorageTestAttemptScoreAdapterTest {

    private LocalStorageTestAttemptScoreAdapter adapter;

    @BeforeEach
    void setup() {
        adapter = new LocalStorageTestAttemptScoreAdapter();
    }

    private TestAttemptScore createScore(
            UUID id,
            UUID attemptId,
            UUID scaleId,
            Integer score,
            String interpretation
    ) {
        TestAttemptScore s = new TestAttemptScore();
        s.setId(id);
        s.setTestAttemptId(attemptId);
        s.setScaleId(scaleId);
        s.setScore(score);
        s.setInterpretation(interpretation);
        return s;
    }

    @Test
    void save_withNullId_generatesId() {

        TestAttemptScore score = createScore(null, UUID.randomUUID(), UUID.randomUUID(), 10, "OK");

        TestAttemptScore saved = adapter.save(score);

        assertNotNull(saved.getId());
        assertTrue(adapter.findById(saved.getId()).isPresent());
    }

    @Test
    void save_withExistingId_keepsId() {

        UUID id = UUID.randomUUID();

        TestAttemptScore score = createScore(id, UUID.randomUUID(), UUID.randomUUID(), 5, "GOOD");

        TestAttemptScore saved = adapter.save(score);

        assertEquals(id, saved.getId());
    }

    @Test
    void saveAll_handlesNullAndExistingIds() {

        TestAttemptScore s1 = createScore(null, UUID.randomUUID(), UUID.randomUUID(), 1, "A");

        UUID id2 = UUID.randomUUID();
        TestAttemptScore s2 = createScore(id2, UUID.randomUUID(), UUID.randomUUID(), 2, "B");

        List<TestAttemptScore> result = adapter.saveAll(List.of(s1, s2));

        assertEquals(2, result.size());
        assertNotNull(result.get(0).getId());
        assertEquals(id2, result.get(1).getId());
    }

    @Test
    void findById_found() {

        TestAttemptScore score = createScore(null, UUID.randomUUID(), UUID.randomUUID(), 3, "OK");

        TestAttemptScore saved = adapter.save(score);

        Optional<TestAttemptScore> found = adapter.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    void findById_notFound() {

        Optional<TestAttemptScore> found = adapter.findById(UUID.randomUUID());

        assertTrue(found.isEmpty());
    }

    @Test
    void findByFilter_allFieldsMatch() {

        UUID id = UUID.randomUUID();
        UUID attemptId = UUID.randomUUID();
        UUID scaleId = UUID.randomUUID();

        TestAttemptScore score = createScore(id, attemptId, scaleId, 10, "GOOD");

        adapter.save(score);

        TestAttemptScore filter = createScore(id, attemptId, scaleId, 10, "good" );

        List<TestAttemptScore> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
    }

    @Test
    void findByFilter_partialMatch_withNulls() {

        UUID attemptId = UUID.randomUUID();

        TestAttemptScore score = createScore(UUID.randomUUID(), attemptId, UUID.randomUUID(), 5, "OK");

        adapter.save(score);

        TestAttemptScore filter = new TestAttemptScore();
        filter.setTestAttemptId(attemptId);

        List<TestAttemptScore> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
    }

    @Test
    void findByFilter_noMatch() {

        TestAttemptScore score = createScore(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 5, "OK");

        adapter.save(score);

        TestAttemptScore filter = new TestAttemptScore();
        filter.setScore(999);

        List<TestAttemptScore> result = adapter.findByFilter(filter);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteById_removesElement() {

        TestAttemptScore score = createScore(null, UUID.randomUUID(), UUID.randomUUID(), 7, "DEL");

        TestAttemptScore saved = adapter.save(score);

        adapter.deleteById(saved.getId());

        assertTrue(adapter.findById(saved.getId()).isEmpty());
    }
}
