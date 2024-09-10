package com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi;

import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.controllers.PatientController;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.Patient;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdatePatient_Success() {
        // Arrange
        Patient existingPatient = new Patient();
        existingPatient.setId(1L);
        existingPatient.setFirstName("John");
        existingPatient.setLastName("Doe");
        existingPatient.setAge(30);

        Patient updatedDetails = new Patient();
        updatedDetails.setFirstName("Jane");
        updatedDetails.setLastName("Doe");
        updatedDetails.setAge(31);

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        // Act
        ResponseEntity<Patient> response = patientController.updatePatient(1L, updatedDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals(31, response.getBody().getAge());
    }

    @Test
    public void testUpdatePatient_NotFound() {
        // Arrange
        Patient updatedDetails = new Patient();
        updatedDetails.setFirstName("Jane");
        updatedDetails.setLastName("Doe");
        updatedDetails.setAge(31);

        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Patient> response = patientController.updatePatient(1L, updatedDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}