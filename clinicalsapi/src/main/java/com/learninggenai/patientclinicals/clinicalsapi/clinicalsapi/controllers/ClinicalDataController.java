package com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.controllers;

import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.dto.ClinicalDataRequest;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.ClinicalData;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.Patient;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos.ClinicalDataRepository;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clinicaldata")
@CrossOrigin(origins = "*")
public class ClinicalDataController {

    @Autowired
    private ClinicalDataRepository clinicalDataRepository;

    @Autowired
    private PatientRepository patientRepository;

    private Patient patient;

    // Create a new clinical data entry
    @PostMapping
    public ClinicalData createClinicalData(@RequestBody ClinicalData clinicalData) {
        return clinicalDataRepository.save(clinicalData);
    }

    // Get all clinical data entries
    @GetMapping
    public List<ClinicalData> getAllClinicalData() {
        return clinicalDataRepository.findAll();
    }

    // Get a single clinical data entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClinicalData> getClinicalDataById(@PathVariable Long id) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            return ResponseEntity.ok(clinicalData.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a clinical data entry
    @PutMapping("/{id}")
    public ResponseEntity<ClinicalData> updateClinicalData(@PathVariable Long id, @RequestBody ClinicalData clinicalDataDetails) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            ClinicalData existingClinicalData = clinicalData.get();
            existingClinicalData.setComponentName(clinicalDataDetails.getComponentName());
            existingClinicalData.setComponentValue(clinicalDataDetails.getComponentValue());
            existingClinicalData.setMeasuredDateTime(clinicalDataDetails.getMeasuredDateTime());
            // Update other fields as necessary
            ClinicalData updatedClinicalData = clinicalDataRepository.save(existingClinicalData);
            return ResponseEntity.ok(updatedClinicalData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a clinical data entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinicalData(@PathVariable Long id) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            clinicalDataRepository.delete(clinicalData.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // method that receives patient id,clinical data and saves it to the database
    @PostMapping("/patients/{id}")
    public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request){
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName(request.getComponentName());
        clinicalData.setComponentValue(request.getComponentValue());

        Patient patient = patientRepository.findById(request.getPatientId()).get();
        clinicalData.setPatient(patient);

        return clinicalDataRepository.save(clinicalData);
    }

}