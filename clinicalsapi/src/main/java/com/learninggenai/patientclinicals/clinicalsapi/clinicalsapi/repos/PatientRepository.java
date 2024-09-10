package com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.Patient;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Custom query methods can be defined here if needed
}