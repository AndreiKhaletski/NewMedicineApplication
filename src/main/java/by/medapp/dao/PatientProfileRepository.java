package by.medapp.dao;

import by.medapp.core.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
    Optional<PatientProfile> findByOldClientGuidContaining(String oldClientGuid);
}
