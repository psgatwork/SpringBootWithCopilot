package com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi;

import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.controllers.ClinicalDataController;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.dto.ClinicalDataRequest;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.ClinicalData;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models.Patient;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos.ClinicalDataRepository;
import com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.repos.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClinicalDataControllerTest {

    @Mock
    private ClinicalDataRepository clinicalDataRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private ClinicalDataController clinicalDataController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveClinicalData_Success() {
        // Arrange
        ClinicalDataRequest request = new ClinicalDataRequest();
        request.setComponentName("Blood Pressure");
        request.setComponentValue("120/80");
        request.setPatientId(1L);

        Patient patient = new Patient();
        patient.setId(1L);

        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName("Blood Pressure");
        clinicalData.setComponentValue("120/80");
        clinicalData.setPatient(patient);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(clinicalData);

        // Act
        ClinicalData result = clinicalDataController.saveClinicalData(request);

        // Assert
        assertEquals("Blood Pressure", result.getComponentName());
        assertEquals("120/80", result.getComponentValue());
        assertEquals(patient, result.getPatient());

        verify(patientRepository, times(1)).findById(1L);
        verify(clinicalDataRepository, times(1)).save(any(ClinicalData.class));
    }

    @Test
    public void testSaveClinicalData_PatientNotFound() {
        // Arrange
        ClinicalDataRequest request = new ClinicalDataRequest();
        request.setComponentName("Blood Pressure");
        request.setComponentValue("120/80");
        request.setPatientId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            clinicalDataController.saveClinicalData(request);
        });

        verify(patientRepository, times(1)).findById(1L);
        verify(clinicalDataRepository, times(0)).save(any(ClinicalData.class));
    }
}