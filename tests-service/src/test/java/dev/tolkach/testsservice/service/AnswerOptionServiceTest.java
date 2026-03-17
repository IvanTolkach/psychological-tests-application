package dev.tolkach.testsservice.service;

import dev.tolkach.testsservice.application.model.AnswerOption;
import dev.tolkach.testsservice.application.model.Question;
import dev.tolkach.testsservice.application.port.out.AnswerOptionRepository;
import dev.tolkach.testsservice.application.port.out.QuestionRepository;
import dev.tolkach.testsservice.application.service.AnswerOptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerOptionServiceTest {

    @Mock
    private AnswerOptionRepository answerOptionRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private AnswerOptionService service;

    private UUID id;
    private UUID questionId;

    private AnswerOption option;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        questionId = UUID.randomUUID();

        option = new AnswerOption();
        option.setId(id);
        option.setQuestionId(questionId);
        option.setText("A");
        option.setScore(1);
    }

    @Test
    void getAnswerOptionsByFilter_withoutQuestionId() {

        when(answerOptionRepository.findByFilter(any()))
                .thenReturn(List.of(option));

        List<AnswerOption> result =
                service.getAnswerOptionsByFilter(new AnswerOption());

        assertEquals(1, result.size());
    }

    @Test
    void getAnswerOptionsByFilter_withQuestionId_ok() {

        AnswerOption filter = new AnswerOption();
        filter.setQuestionId(questionId);

        when(questionRepository.findById(questionId))
                .thenReturn(Optional.of(new Question()));

        when(answerOptionRepository.findByFilter(filter))
                .thenReturn(List.of(option));

        List<AnswerOption> result =
                service.getAnswerOptionsByFilter(filter);

        assertEquals(1, result.size());
    }

    @Test
    void getAnswerOptionsByFilter_questionNotFound() {

        AnswerOption filter = new AnswerOption();
        filter.setQuestionId(questionId);

        when(questionRepository.findById(questionId))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.getAnswerOptionsByFilter(filter)
        );
    }

    @Test
    void getAnswerOptionById_ok() {

        when(answerOptionRepository.findById(id))
                .thenReturn(Optional.of(option));

        AnswerOption result =
                service.getAnswerOptionById(id);

        assertEquals(option, result);
    }

    @Test
    void getAnswerOptionById_notFound() {

        when(answerOptionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.getAnswerOptionById(id)
        );
    }

    @Test
    void createAnswerOption_ok() {

        option.setId(null);

        when(questionRepository.findById(questionId))
                .thenReturn(Optional.of(new Question()));

        when(answerOptionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(answerOptionRepository.save(option))
                .thenReturn(option);

        AnswerOption result =
                service.createUpdateAnswerOption(option);

        assertEquals(option, result);
    }

    @Test
    void createAnswerOption_duplicateText() {

        option.setId(null);

        AnswerOption existing = new AnswerOption();
        existing.setId(UUID.randomUUID());
        existing.setQuestionId(questionId);
        existing.setText("A");

        when(questionRepository.findById(questionId))
                .thenReturn(Optional.of(new Question()));

        when(answerOptionRepository.findByFilter(any()))
                .thenReturn(List.of(existing));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUpdateAnswerOption(option)
        );
    }

    @Test
    void updateAnswerOption_ok() {

        AnswerOption existing = new AnswerOption();
        existing.setId(id);
        existing.setQuestionId(questionId);
        existing.setText("old");

        when(questionRepository.findById(questionId))
                .thenReturn(Optional.of(new Question()));

        when(answerOptionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(answerOptionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        when(answerOptionRepository.save(existing))
                .thenReturn(existing);

        AnswerOption result =
                service.createUpdateAnswerOption(option);

        assertEquals(existing, result);
    }

    @Test
    void updateAnswerOption_notFound() {

        when(questionRepository.findById(questionId))
                .thenReturn(Optional.of(new Question()));

        when(answerOptionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(answerOptionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.createUpdateAnswerOption(option)
        );
    }

    @Test
    void deleteAnswerOption_ok() {

        when(answerOptionRepository.findById(id))
                .thenReturn(Optional.of(option));

        service.deleteAnswerOption(id);

        verify(answerOptionRepository).deleteById(id);
    }

    @Test
    void deleteAnswerOption_notFound() {

        when(answerOptionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.deleteAnswerOption(id)
        );
    }
}
