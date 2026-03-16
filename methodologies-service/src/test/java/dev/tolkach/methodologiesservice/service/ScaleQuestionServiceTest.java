package dev.tolkach.methodologiesservice.service;

import dev.tolkach.methodologiesservice.application.model.Scale;
import dev.tolkach.methodologiesservice.application.model.ScaleQuestion;
import dev.tolkach.methodologiesservice.application.port.out.ScaleQuestionRepository;
import dev.tolkach.methodologiesservice.application.port.out.ScaleRepository;
import dev.tolkach.methodologiesservice.application.port.out.TestsPort;
import dev.tolkach.methodologiesservice.application.service.ScaleQuestionService;
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
class ScaleQuestionServiceTest {

    @Mock
    ScaleQuestionRepository scaleQuestionRepository;

    @Mock
    ScaleRepository scaleRepository;

    @Mock
    TestsPort testsPort;

    @InjectMocks
    ScaleQuestionService service;

    private UUID scaleId;
    private UUID questionId;
    private UUID linkId;

    private ScaleQuestion scaleQuestion;

    @BeforeEach
    void setUp() {
        scaleId = UUID.randomUUID();
        questionId = UUID.randomUUID();
        linkId = UUID.randomUUID();

        scaleQuestion = new ScaleQuestion();
        scaleQuestion.setId(linkId);
        scaleQuestion.setScaleId(scaleId);
        scaleQuestion.setQuestionId(questionId);
    }

    @Test
    void getScaleQuestionsByFilter_scaleAndQuestionExists_returnsList() {
        List<ScaleQuestion> list = List.of(scaleQuestion);

        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));
        when(scaleQuestionRepository.findByFilter(scaleQuestion)).thenReturn(list);

        List<ScaleQuestion> result = service.getScaleQuestionsByFilter(scaleQuestion);
        assertEquals(list, result);

        verify(scaleRepository).findById(scaleId);
        verify(testsPort).validateQuestionExists(questionId);
        verify(scaleQuestionRepository).findByFilter(scaleQuestion);
    }

    @Test
    void getScaleQuestionsByFilter_scaleNotFound_throws() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.getScaleQuestionsByFilter(scaleQuestion));
        assertTrue(ex.getMessage().contains("Scale not found"));
    }

    @Test
    void getScaleQuestionsByFilter_questionNull_callsRepositoryOnlyForScale() {
        scaleQuestion.setQuestionId(null);
        List<ScaleQuestion> list = List.of(scaleQuestion);

        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));
        when(scaleQuestionRepository.findByFilter(scaleQuestion)).thenReturn(list);

        List<ScaleQuestion> result = service.getScaleQuestionsByFilter(scaleQuestion);
        assertEquals(list, result);

        verify(scaleRepository).findById(scaleId);
        verify(testsPort, never()).validateQuestionExists(any());
        verify(scaleQuestionRepository).findByFilter(scaleQuestion);
    }

    @Test
    void createScaleQuestion_success() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));
        when(scaleQuestionRepository.findByFilter(any())).thenReturn(List.of());
        when(scaleQuestionRepository.save(scaleQuestion)).thenReturn(scaleQuestion);

        ScaleQuestion result = service.createScaleQuestion(scaleQuestion);
        assertEquals(scaleQuestion, result);
    }

    @Test
    void createScaleQuestion_scaleNotFound_throws() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.createScaleQuestion(scaleQuestion));
        assertTrue(ex.getMessage().contains("Scale not found"));
    }

    @Test
    void createScaleQuestion_linkAlreadyExists_throws() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));
        doNothing().when(testsPort).validateQuestionExists(questionId);
        when(scaleQuestionRepository.findByFilter(any())).thenReturn(List.of(scaleQuestion));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createScaleQuestion(scaleQuestion));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void deleteScaleQuestion_success() {
        when(scaleQuestionRepository.findById(linkId)).thenReturn(Optional.of(scaleQuestion));
        doNothing().when(scaleQuestionRepository).deleteById(linkId);

        service.deleteScaleQuestion(linkId);

        verify(scaleQuestionRepository).findById(linkId);
        verify(scaleQuestionRepository).deleteById(linkId);
    }

    @Test
    void deleteScaleQuestion_notFound_throws() {
        when(scaleQuestionRepository.findById(linkId)).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.deleteScaleQuestion(linkId));
        assertTrue(ex.getMessage().contains("not found"));
    }
}