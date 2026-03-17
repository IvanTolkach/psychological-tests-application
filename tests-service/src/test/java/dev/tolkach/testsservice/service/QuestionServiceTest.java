package dev.tolkach.testsservice.service;

import dev.tolkach.testsservice.application.model.Question;
import dev.tolkach.testsservice.application.port.out.QuestionRepository;
import dev.tolkach.testsservice.application.port.out.TestRepository;
import dev.tolkach.testsservice.application.service.QuestionService;
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
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private QuestionService service;

    private UUID id;
    private UUID testId;

    private Question question;

    @BeforeEach
    void setup() {

        id = UUID.randomUUID();
        testId = UUID.randomUUID();

        question = new Question();
        question.setId(id);
        question.setTestId(testId);
        question.setText("Q");
        question.setPosition(1);
    }

    @Test
    void getQuestionsByFilter_noTest() {

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of(question));

        List<Question> result =
                service.getQuestionsByFilter(new Question());

        assertEquals(1, result.size());
    }

    @Test
    void getQuestionsByFilter_withTest_ok() {

        Question filter = new Question();
        filter.setTestId(testId);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findByFilter(filter))
                .thenReturn(List.of(question));

        List<Question> result =
                service.getQuestionsByFilter(filter);

        assertEquals(1, result.size());
    }

    @Test
    void getQuestionsByFilter_testNotFound() {

        Question filter = new Question();
        filter.setTestId(testId);

        when(testRepository.findById(testId))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.getQuestionsByFilter(filter)
        );
    }

    @Test
    void getQuestionById_ok() {

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(question));

        Question result =
                service.getQuestionById(id);

        assertEquals(question, result);
    }

    @Test
    void getQuestionById_notFound() {

        when(questionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.getQuestionById(id)
        );
    }

    @Test
    void createQuestion_defaultPosition() {

        question.setId(null);
        question.setPosition(null);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(2);

        when(questionRepository.save(question))
                .thenReturn(question);

        Question result =
                service.createUpdateQuestion(question);

        assertEquals(3, result.getPosition());
    }

    @Test
    void createQuestion_insertInside() {

        question.setId(null);
        question.setPosition(1);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(5);

        when(questionRepository.save(question))
                .thenReturn(question);

        Question result =
                service.createUpdateQuestion(question);

        verify(questionRepository)
                .shiftDown(testId, 1);

        assertEquals(1, result.getPosition());
    }

    @Test
    void createQuestion_duplicateText() {

        question.setId(null);

        Question existing = new Question();
        existing.setId(UUID.randomUUID());

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of(existing));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUpdateQuestion(question)
        );
    }

    @Test
    void create_textNull_shouldSkipUniqueCheck() {

        question.setId(null);
        question.setText(null);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(0);

        when(questionRepository.save(question))
                .thenReturn(question);

        Question result =
                service.createUpdateQuestion(question);

        assertNotNull(result);
    }

    @Test
    void create_textBlank_shouldSkipUniqueCheck() {

        question.setId(null);
        question.setText("   ");

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(0);

        when(questionRepository.save(question))
                .thenReturn(question);

        Question result =
                service.createUpdateQuestion(question);

        assertNotNull(result);
    }

    @Test
    void update_samePosition() {

        Question existing = new Question();
        existing.setId(id);
        existing.setTestId(testId);
        existing.setPosition(1);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(5);

        when(questionRepository.save(existing))
                .thenReturn(existing);

        question.setPosition(1);

        Question result =
                service.createUpdateQuestion(question);

        assertEquals(existing, result);
    }

    @Test
    void update_moveUp() {

        Question existing = new Question();
        existing.setId(id);
        existing.setTestId(testId);
        existing.setPosition(5);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(10);

        when(questionRepository.save(existing))
                .thenReturn(existing);

        question.setPosition(2);

        service.createUpdateQuestion(question);

        verify(questionRepository)
                .shiftForMoveUp(testId, 2, 5);
    }

    @Test
    void update_moveDown() {

        Question existing = new Question();
        existing.setId(id);
        existing.setTestId(testId);
        existing.setPosition(2);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(10);

        when(questionRepository.save(existing))
                .thenReturn(existing);

        question.setPosition(6);

        service.createUpdateQuestion(question);

        verify(questionRepository)
                .shiftForMoveDown(testId, 2, 6);
    }

    @Test
    void update_moveToAnotherTest() {

        Question existing = new Question();
        existing.setId(id);
        existing.setTestId(UUID.randomUUID());
        existing.setPosition(1);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(questionRepository.getMaxPosition(any()))
                .thenReturn(5);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUpdateQuestion(question)
        );
    }

    @Test
    void update_newPositionZero_shouldSetOne() {

        Question existing = new Question();
        existing.setId(id);
        existing.setTestId(testId);
        existing.setPosition(5);

        question.setPosition(0);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(10);

        when(questionRepository.save(existing))
                .thenReturn(existing);

        service.createUpdateQuestion(question);

        verify(questionRepository)
                .shiftForMoveUp(testId, 1, 5);
    }

    @Test
    void update_newPositionGreaterThanMax_shouldClamp() {

        Question existing = new Question();
        existing.setId(id);
        existing.setTestId(testId);
        existing.setPosition(2);

        question.setPosition(100);

        when(testRepository.findById(testId))
                .thenReturn(Optional.of(new dev.tolkach.testsservice.application.model.Test()));

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        when(questionRepository.findByFilter(any()))
                .thenReturn(List.of());

        when(questionRepository.getMaxPosition(testId))
                .thenReturn(5);

        when(questionRepository.save(existing))
                .thenReturn(existing);

        service.createUpdateQuestion(question);

        verify(questionRepository)
                .shiftForMoveDown(testId, 2, 5);
    }

    @Test
    void delete_withShift() {

        question.setPosition(3);

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(question));

        service.deleteQuestion(id);

        verify(questionRepository).deleteById(id);
        verify(questionRepository)
                .shiftUp(testId, 3);
    }

    @Test
    void delete_withoutShift() {

        question.setPosition(0);

        when(questionRepository.findById(id))
                .thenReturn(Optional.of(question));

        service.deleteQuestion(id);

        verify(questionRepository).deleteById(id);
    }

    @Test
    void delete_notFound() {

        when(questionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.deleteQuestion(id)
        );
    }
}