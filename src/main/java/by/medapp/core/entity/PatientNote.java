package by.medapp.core.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(schema = "app", name = "patient_note")
public class PatientNote {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createdDateTime")
    private LocalDateTime createdDateTime;
    @Column(name = "lastModifiedDateTime")
    private LocalDateTime lastModifiedDateTime;
    private String note;

    @ManyToOne
    @Column(name = "createdByUser")
    @JoinColumn(name = "created_by_user_id")
    private CompanyUser createdByUser;

    @ManyToOne
    @Column(name = "lastModifiedByUser")
    @JoinColumn(name = "last_modified_by_user_id")
    private CompanyUser lastModifiedByUser;

    @ManyToOne
    @Column(name = "patient")
    @JoinColumn(name = "patient_id")
    private PatientProfile patient;

    public PatientNote() {
    }

    public PatientNote(Long id,
                       LocalDateTime createdDateTime,
                       LocalDateTime lastModifiedDateTime,
                       String note,
                       CompanyUser createdByUser,
                       CompanyUser lastModifiedByUser,
                       PatientProfile patient) {
        this.id = id;
        this.createdDateTime = createdDateTime;
        this.lastModifiedDateTime = lastModifiedDateTime;
        this.note = note;
        this.createdByUser = createdByUser;
        this.lastModifiedByUser = lastModifiedByUser;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(LocalDateTime lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CompanyUser getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(CompanyUser createdByUser) {
        this.createdByUser = createdByUser;
    }

    public CompanyUser getLastModifiedByUser() {
        return lastModifiedByUser;
    }

    public void setLastModifiedByUser(CompanyUser lastModifiedByUser) {
        this.lastModifiedByUser = lastModifiedByUser;
    }

    public PatientProfile getPatient() {
        return patient;
    }

    public void setPatient(PatientProfile patient) {
        this.patient = patient;
    }
}
