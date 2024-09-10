package com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.controllers;

import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.Patient;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientRepository patientRepository;

    // Create a new patient
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        logger.debug("Creating new patient: {}", patient);
        return patientRepository.save(patient);
    }

    // Get all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        logger.debug("Fetching all patients");
        return patientRepository.findAll();
    }

    // Get a single patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.debug("Fetching patient with id: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            logger.info("Patient found with id: {}", id);
            return ResponseEntity.ok(patient.get());
        } else {
            logger.warn("Patient not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Update a patient
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        logger.debug("Updating patient with id: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            Patient existingPatient = patient.get();
            existingPatient.setFirstName(patientDetails.getFirstName());
            existingPatient.setLastName(patientDetails.getLastName());
            existingPatient.setAge(patientDetails.getAge());
            // Update other fields as necessary
            Patient updatedPatient = patientRepository.save(existingPatient);
            logger.info("Patient updated with id: {}", id);
            return ResponseEntity.ok(updatedPatient);
        } else {
            logger.warn("Patient not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a patient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        logger.debug("Deleting patient with id: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patientRepository.delete(patient.get());
            logger.info("Patient deleted with id: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Patient not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}