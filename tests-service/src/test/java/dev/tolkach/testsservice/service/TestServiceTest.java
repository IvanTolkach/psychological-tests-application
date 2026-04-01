package dev.tolkach.testsservice.service;

import dev.tolkach.testsservice.application.model.TestFilter;
import dev.tolkach.testsservice.application.port.out.MethodologiesPort;
import dev.tolkach.testsservice.application.port.out.TestRepository;
import dev.tolkach.testsservice.application.port.out.UsersPort;
import dev.tolkach.testsservice.application.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceTest {

    @Mock
    private TestRepository testRepository;

    @Mock
    private MethodologiesPort methodologiesPort;

    @Mock
    private UsersPort usersPort;

    @InjectMocks
    private TestService service;

    private UUID testId;
    private UUID methodologyId;
    private UUID adminId;
    private UUID anotherAdminId;

    private dev.tolkach.testsservice.application.model.Test test;
    private dev.tolkach.testsservice.application.model.Test existingTest;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        methodologyId = UUID.randomUUID();
        adminId = UUID.randomUUID();
        anotherAdminId = UUID.randomUUID();

        test = new dev.tolkach.testsservice.application.model.Test();
        test.setId(testId);
        test.setName("Шкала Бека 3-5 курс");
        test.setMethodologyId(methodologyId);
        test.setIsActive(false);

        existingTest = new dev.tolkach.testsservice.application.model.Test();
        existingTest.setId(testId);
        existingTest.setName("Старое название");
        existingTest.setMethodologyId(methodologyId);
        existingTest.setCreatedBy(adminId);
        existingTest.setCreatedAt(LocalDateTime.now().minusDays(5));
        existingTest.setUpdatedBy(adminId);
        existingTest.setUpdatedAt(LocalDateTime.now().minusDays(3));
        existingTest.setIsActive(true);
    }

    @Test
    void getTestsByFilter_noFilters() {
        TestFilter filter = new TestFilter();

        when(testRepository.findByFilter(filter)).thenReturn(List.of(test));

        List<dev.tolkach.testsservice.application.model.Test> result = service.getTestsByFilter(filter);

        assertEquals(1, result.size());
        verifyNoInteractions(methodologiesPort, usersPort);
    }

    @Test
    void getTestsByFilter_withMethodologyId() {
        TestFilter filter = new TestFilter();
        filter.setMethodologyId(methodologyId);

        doNothing().when(methodologiesPort).validateMethodologyExists(methodologyId);
        when(testRepository.findByFilter(filter)).thenReturn(List.of(test));

        List<dev.tolkach.testsservice.application.model.Test> result = service.getTestsByFilter(filter);

        assertEquals(1, result.size());
        verify(methodologiesPort).validateMethodologyExists(methodologyId);
    }

    @Test
    void getTestsByFilter_withCreatedBy() {
        TestFilter filter = new TestFilter();
        filter.setCreatedBy(adminId);

        doNothing().when(usersPort).validateAdminExists(adminId);
        when(testRepository.findByFilter(filter)).thenReturn(List.of(test));

        List<dev.tolkach.testsservice.application.model.Test> result = service.getTestsByFilter(filter);

        assertEquals(1, result.size());
        verify(usersPort).validateAdminExists(adminId);
    }

    @Test
    void getTestsByFilter_withUpdatedBy() {
        TestFilter filter = new TestFilter();
        filter.setUpdatedBy(anotherAdminId);

        doNothing().when(usersPort).validateAdminExists(anotherAdminId);
        when(testRepository.findByFilter(filter)).thenReturn(List.of(test));

        List<dev.tolkach.testsservice.application.model.Test> result =
                service.getTestsByFilter(filter);

        assertEquals(1, result.size());
        verify(usersPort).validateAdminExists(anotherAdminId);
        verifyNoInteractions(methodologiesPort);
    }

    @Test
    void getTestById_found() {
        when(testRepository.findById(testId)).thenReturn(Optional.of(test));

        dev.tolkach.testsservice.application.model.Test result = service.getTestById(testId);

        assertEquals(test, result);
    }

    @Test
    void getTestById_notFound() {
        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.getTestById(testId),
                "Test not found with id: " + testId);
    }

    @Test
    void createUpdateTest_create_new() {
        var newTest = new dev.tolkach.testsservice.application.model.Test();
        newTest.setName("Новый тест Горской");
        newTest.setMethodologyId(methodologyId);

        try (MockedStatic<SecurityContextHolder> mockedHolder = mockStatic(SecurityContextHolder.class)) {

            var context = mock(SecurityContext.class);
            var auth = mock(Authentication.class);
            var details = new HashMap<String, Object>();
            details.put("adminId", adminId);
            when(auth.getDetails()).thenReturn(details);
            when(context.getAuthentication()).thenReturn(auth);
            mockedHolder.when(SecurityContextHolder::getContext).thenReturn(context);

            doNothing().when(methodologiesPort).validateMethodologyExists(methodologyId);

            when(testRepository.findByFilter(argThat(f ->
                    f.getMethodologyId().equals(methodologyId) &&
                            "Новый тест Горской".equals(f.getName())
            ))).thenReturn(List.of());

            when(testRepository.save(argThat(t ->
                    t.getId() == null &&
                            adminId.equals(t.getCreatedBy()) &&
                            adminId.equals(t.getUpdatedBy()) &&
                            Boolean.FALSE.equals(t.getIsActive()) &&
                            t.getCreatedAt() != null &&
                            t.getUpdatedAt() != null
            ))).thenAnswer(invocation -> {
                var t = invocation.getArgument(0, dev.tolkach.testsservice.application.model.Test.class);
                t.setId(UUID.randomUUID());
                return t;
            });

            var result = service.createUpdateTest(newTest);

            assertNotNull(result.getId(), "ID должен быть сгенерирован");
            assertEquals(adminId, result.getCreatedBy(), "createdBy должен быть текущим админом");
            assertEquals(adminId, result.getUpdatedBy(), "updatedBy должен быть текущим админом");
            assertFalse(result.getIsActive(), "Новый тест по умолчанию неактивен");
            assertNotNull(result.getCreatedAt(), "createdAt должен быть установлен");
            assertNotNull(result.getUpdatedAt(), "updatedAt должен быть установлен");
            assertEquals(result.getCreatedAt(), result.getUpdatedAt(), "при создании даты должны совпадать");

            verify(testRepository, times(1)).save(any());
        }
    }

    @Test
    void createUpdateTest_create_nameConflict() {
        dev.tolkach.testsservice.application.model.Test newTest = new dev.tolkach.testsservice.application.model.Test();
        newTest.setName("Шкала Бека 3-5 курс");
        newTest.setMethodologyId(methodologyId);

        try (MockedStatic<SecurityContextHolder> mockedHolder = mockStatic(SecurityContextHolder.class)) {

            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            Map<String, Object> details = new HashMap<>();
            details.put("adminId", adminId);

            when(auth.getDetails()).thenReturn(details);
            when(context.getAuthentication()).thenReturn(auth);
            mockedHolder.when(SecurityContextHolder::getContext).thenReturn(context);

            doNothing().when(methodologiesPort).validateMethodologyExists(methodologyId);

            when(testRepository.findByFilter(argThat(f ->
                    f.getMethodologyId().equals(methodologyId) &&
                            f.getName().equals("Шкала Бека 3-5 курс")
            ))).thenReturn(List.of(existingTest));

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> service.createUpdateTest(newTest));

            assertEquals(
                    "Test with name 'Шкала Бека 3-5 курс' already exists for methodology " + methodologyId,
                    ex.getMessage()
            );
        }
    }

    @Test
    void createUpdateTest_update_existing() {
        dev.tolkach.testsservice.application.model.Test updateDto = new dev.tolkach.testsservice.application.model.Test();
        updateDto.setId(testId);
        updateDto.setName("Обновлённое название");
        updateDto.setMethodologyId(methodologyId);

        try (MockedStatic<SecurityContextHolder> mockedHolder = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            Map<String, Object> details = new HashMap<>();
            details.put("adminId", anotherAdminId);

            when(auth.getDetails()).thenReturn(details);
            when(context.getAuthentication()).thenReturn(auth);
            mockedHolder.when(SecurityContextHolder::getContext).thenReturn(context);

            doNothing().when(methodologiesPort).validateMethodologyExists(methodologyId);
            when(testRepository.findById(testId)).thenReturn(Optional.of(existingTest));

            when(testRepository.findByFilter(argThat(f ->
                    Objects.equals(f.getMethodologyId(), methodologyId) &&
                            Objects.equals(f.getName(), "Обновлённое название")
            ))).thenReturn(List.of());

            when(testRepository.save(argThat(t ->
                    t.getId().equals(testId) &&
                            "Обновлённое название".equals(t.getName()) &&
                            anotherAdminId.equals(t.getUpdatedBy())
            ))).thenAnswer(invocation -> invocation.<dev.tolkach.testsservice.application.model.Test>getArgument(0));

            dev.tolkach.testsservice.application.model.Test result = service.createUpdateTest(updateDto);

            assertEquals("Обновлённое название", result.getName());
            assertEquals(anotherAdminId, result.getUpdatedBy());
            assertNotNull(result.getUpdatedAt());

            verify(usersPort, never()).validateAdminExists(any());
        }
    }

    @Test
    void createUpdateTest_update_notFound() {
        dev.tolkach.testsservice.application.model.Test updateDto = new dev.tolkach.testsservice.application.model.Test();
        updateDto.setId(testId);
        updateDto.setName("Что-то");

        try (MockedStatic<SecurityContextHolder> mockedHolder = mockStatic(SecurityContextHolder.class)) {

            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            Map<String, Object> details = new HashMap<>();
            details.put("adminId", adminId);

            when(auth.getDetails()).thenReturn(details);
            when(context.getAuthentication()).thenReturn(auth);
            mockedHolder.when(SecurityContextHolder::getContext).thenReturn(context);

            when(testRepository.findById(testId)).thenReturn(Optional.empty());

            NoSuchElementException thrown = assertThrows(
                    NoSuchElementException.class,
                    () -> service.createUpdateTest(updateDto)
            );

            assertEquals("Test not found with id: " + testId, thrown.getMessage());
        }
    }

    @Test
    void createUpdateTest_update_existing_withCreatedBy_callsValidateAdminExists() {
        dev.tolkach.testsservice.application.model.Test updateDto = new dev.tolkach.testsservice.application.model.Test();
        updateDto.setId(testId);
        updateDto.setName("Новое название для проверки");
        updateDto.setMethodologyId(methodologyId);
        updateDto.setCreatedBy(adminId);
        updateDto.setUpdatedBy(anotherAdminId);

        try (MockedStatic<SecurityContextHolder> mockedHolder = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            Map<String, Object> details = new HashMap<>();
            details.put("adminId", anotherAdminId);
            when(auth.getDetails()).thenReturn(details);
            when(context.getAuthentication()).thenReturn(auth);
            mockedHolder.when(SecurityContextHolder::getContext).thenReturn(context);

            doNothing().when(methodologiesPort).validateMethodologyExists(methodologyId);
            when(testRepository.findById(testId)).thenReturn(Optional.of(existingTest));
            when(testRepository.findByFilter(argThat(f ->
                    Objects.equals(f.getMethodologyId(), methodologyId) &&
                            Objects.equals(f.getName(), "Новое название для проверки")
            ))).thenReturn(List.of());
            when(testRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            dev.tolkach.testsservice.application.model.Test result = service.createUpdateTest(updateDto);

            assertEquals("Новое название для проверки", result.getName());
            assertEquals(anotherAdminId, result.getUpdatedBy());
            assertNotNull(result.getUpdatedAt());
        }
    }

    @Test
    void updateTestStatus_toggle_true_to_false() {
        when(testRepository.findById(testId)).thenReturn(Optional.of(existingTest));
        when(testRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.updateTestStatus(testId);

        ArgumentCaptor<dev.tolkach.testsservice.application.model.Test> captor = ArgumentCaptor.forClass(dev.tolkach.testsservice.application.model.Test.class);
        verify(testRepository).save(captor.capture());
        assertFalse(captor.getValue().getIsActive());
    }

    @Test
    void updateTestStatus_toggle_false_to_true() {
        existingTest.setIsActive(false);
        when(testRepository.findById(testId)).thenReturn(Optional.of(existingTest));

        service.updateTestStatus(testId);

        ArgumentCaptor<dev.tolkach.testsservice.application.model.Test> captor = ArgumentCaptor.forClass(dev.tolkach.testsservice.application.model.Test.class);
        verify(testRepository).save(captor.capture());
        assertTrue(captor.getValue().getIsActive());
    }

    @Test
    void updateTestStatus_notFound() {
        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.updateTestStatus(testId));
    }

    @Test
    void deleteTest_exists() {
        when(testRepository.findById(testId)).thenReturn(Optional.of(test));

        service.deleteTest(testId);

        verify(testRepository).deleteById(testId);
    }

    @Test
    void deleteTest_notFound() {
        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.deleteTest(testId));
    }
}
