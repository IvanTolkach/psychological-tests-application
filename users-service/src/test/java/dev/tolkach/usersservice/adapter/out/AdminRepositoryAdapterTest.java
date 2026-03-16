package dev.tolkach.usersservice.adapter.out;

import dev.tolkach.usersservice.adapter.out.persistence.entity.AdminEntity;
import dev.tolkach.usersservice.adapter.out.persistence.mapper.AdminMapper;
import dev.tolkach.usersservice.adapter.out.persistence.repository.AdminRepositoryAdapter;
import dev.tolkach.usersservice.adapter.out.persistence.repository.JpaAdminRepository;
import dev.tolkach.usersservice.application.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminRepositoryAdapterTest {

    @Mock
    JpaAdminRepository jpaAdminRepository;

    @Mock
    AdminMapper adminMapper;

    @InjectMocks
    AdminRepositoryAdapter adapter;

    Admin admin;
    AdminEntity entity;

    @BeforeEach
    void setup() {
        admin = new Admin();
        admin.setId(UUID.randomUUID());

        entity = new AdminEntity();
        entity.setId(admin.getId());
    }

    @Test
    void save_success() {
        when(adminMapper.toEntity(admin)).thenReturn(entity);
        when(jpaAdminRepository.save(entity)).thenReturn(entity);
        when(adminMapper.toDomain(entity)).thenReturn(admin);

        Admin result = adapter.save(admin);

        assertEquals(admin, result);
    }

    @Test
    void findById_present() {
        when(jpaAdminRepository.findById(admin.getId())).thenReturn(Optional.of(entity));
        when(adminMapper.toDomain(entity)).thenReturn(admin);

        Optional<Admin> result = adapter.findById(admin.getId());
        assertTrue(result.isPresent());
        assertEquals(admin, result.get());
    }

    @Test
    void findById_empty() {
        when(jpaAdminRepository.findById(admin.getId())).thenReturn(Optional.empty());
        assertTrue(adapter.findById(admin.getId()).isEmpty());
    }

    @Test
    void findByEmail_present() {
        when(jpaAdminRepository.findByEmail("email")).thenReturn(Optional.of(entity));
        when(adminMapper.toDomain(entity)).thenReturn(admin);

        Optional<Admin> result = adapter.findByEmail("email");
        assertTrue(result.isPresent());
        assertEquals(admin, result.get());
    }

    @Test
    void findByEmail_empty() {
        when(jpaAdminRepository.findByEmail("email")).thenReturn(Optional.empty());
        assertTrue(adapter.findByEmail("email").isEmpty());
    }

    @Test
    void findByFilter_returnsList() {
        when(jpaAdminRepository.findAll(any(Specification.class))).thenReturn(List.of(entity));
        when(adminMapper.toDomain(entity)).thenReturn(admin);

        List<Admin> result = adapter.findByFilter(new Admin());
        assertEquals(1, result.size());
        assertEquals(admin, result.getFirst());
    }
}
