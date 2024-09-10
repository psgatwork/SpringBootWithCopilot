package com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.controllers;

import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.Patient;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // Create a new patient
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    // Get all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get a single patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a patient
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            Patient existingPatient = patient.get();
            existingPatient.setFirstName(patientDetails.getFirstName());
            existingPatient.setLastName(patientDetails.getLastName());
            existingPatient.setAge(patientDetails.getAge());
            // Update other fields as necessary
            Patient updatedPatient = patientRepository.save(existingPatient);
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a patient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patientRepository.delete(patient.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}