package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAdminRepository extends JpaRepository<AdminEntity, UUID>, JpaSpecificationExecutor<AdminEntity> {
    Optional<AdminEntity> findByEmail(String email);
}
