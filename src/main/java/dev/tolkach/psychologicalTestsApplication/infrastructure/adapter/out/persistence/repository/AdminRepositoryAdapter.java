package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.Admin;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.AdminRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.AdminEntity;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.AdminMapper;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification.AdminSpecification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AdminRepositoryAdapter implements AdminRepository {
    private final JpaAdminRepository jpaAdminRepository;
    private final AdminMapper adminMapper;

    public AdminRepositoryAdapter(JpaAdminRepository jpaAdminRepository, AdminMapper adminMapper) {
        this.jpaAdminRepository = jpaAdminRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin save(Admin admin) {
        AdminEntity entity = adminMapper.toEntity(admin);
        AdminEntity saved = jpaAdminRepository.save(entity);
        return adminMapper.toDomain(saved);
    }

    @Override
    public Optional<Admin> findById(UUID id) {
        return jpaAdminRepository.findById(id)
                .map(adminMapper::toDomain);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return jpaAdminRepository.findByEmail(email)
                .map(adminMapper::toDomain);
    }

    @Override
    public List<Admin> findByFilter(Admin filter) {
        return jpaAdminRepository.findAll(AdminSpecification.filterBy(filter))
                .stream()
                .map(adminMapper::toDomain)
                .collect(Collectors.toList());
    }
}
