package by.medapp.service.converter;

import by.medapp.core.dto.NoteDTO;
import by.medapp.core.entity.PatientNote;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NoteConverter {

    public static PatientNote toEntity(NoteDTO noteDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PatientNote patientNote = new PatientNote();
        patientNote.setCreatedDateTime(LocalDateTime.parse(noteDTO.getCreatedDateTime(), formatter));
        patientNote.setLastModifiedDateTime(LocalDateTime.parse(noteDTO.getModifiedDateTime(), formatter));
        patientNote.setNote(noteDTO.getComments());

        return patientNote;
    }
}
