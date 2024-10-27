package by.medapp.core.entity;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "patient_profile")
public class PatientProfile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "oldClientGuid")
    private String oldClientGuid;
    @Column(name = "statusId")
    private int statusId;

    public PatientProfile() {
    }

    public PatientProfile(Long id,
                          String firstName,
                          String lastName,
                          String oldClientGuid,
                          int statusId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oldClientGuid = oldClientGuid;
        this.statusId = statusId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOldClientGuid() {
        return oldClientGuid;
    }

    public void setOldClientGuid(String oldClientGuid) {
        this.oldClientGuid = oldClientGuid;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
