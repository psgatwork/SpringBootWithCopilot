package main.java.com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.java.com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.ClinicalData;

@Repository
public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Long> {
    // Custom query methods can be defined here if needed
}