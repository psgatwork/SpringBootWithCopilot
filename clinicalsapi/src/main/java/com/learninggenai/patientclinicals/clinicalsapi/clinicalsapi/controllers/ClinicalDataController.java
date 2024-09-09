package main.java.com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.controllers;

import main.java.com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.ClinicalData;
import main.java.com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos.ClinicalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clinicaldata")
public class ClinicalDataController {

    @Autowired
    private ClinicalDataRepository clinicalDataRepository;

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
}