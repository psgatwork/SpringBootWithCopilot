package main.java.com.learninggenai.patientclinicals.clinicalsapi.clinicalsapi.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Table(name = "clinicaldata")
public class ClinicalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String componentName;
    private String componentValue;
    private Timestamp measuredDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private Patient patient;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentValue() {
        return componentValue;
    }

    public void setComponentValue(String componentValue) {
        this.componentValue = componentValue;
    }

    public Timestamp getMeasuredDateTime() {
        return measuredDateTime;
    }

    public void setMeasuredDateTime(Timestamp measuredDateTime) {
        this.measuredDateTime = measuredDateTime;
    }

    // Optional: Override toString, equals, and hashCode
    @Override
    public String toString() {
        return "ClinicalData{" +
                "id=" + id +
                ", componentName='" + componentName + '\'' +
                ", componentValue='" + componentValue + '\'' +
                ", measuredDateTime=" + measuredDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClinicalData that = (ClinicalData) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}