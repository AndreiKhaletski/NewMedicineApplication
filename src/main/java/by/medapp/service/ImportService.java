package by.medapp.service;

import by.medapp.core.NoteRequest;
import by.medapp.core.dto.ClientDTO;
import by.medapp.core.dto.NoteDTO;
import by.medapp.core.entity.CompanyUser;
import by.medapp.core.entity.PatientNote;
import by.medapp.core.entity.PatientProfile;
import by.medapp.dao.CompanyUserRepository;
import by.medapp.dao.PatientNoteRepository;
import by.medapp.dao.PatientProfileRepository;
import by.medapp.service.api.IImportService;
import by.medapp.service.converter.NoteConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ImportService implements IImportService {
    public static final String IMPORT_FROM_DATE = "2019-01-01";
    public static final String IMPORT_TO_DATE = "2021-12-31";
    private static final Set<Integer> ALLOWED_CLIENT_STATUSES = Set.of(200, 210, 230);
    public static final String CRON_SCHEDULE_EVERY_2_HOURS_AT_15_MIN = "0 15 0/2 * * *";

    private final ClientService clientService;
    private final PatientProfileRepository patientProfileRepository;
    private final CompanyUserRepository companyUserRepository;
    private final PatientNoteRepository patientNoteRepository;

    public ImportService(ClientService clientService,
                         PatientProfileRepository patientProfileRepository,
                         CompanyUserRepository companyUserRepository,
                         PatientNoteRepository patientNoteRepository) {
        this.clientService = clientService;
        this.patientProfileRepository = patientProfileRepository;
        this.companyUserRepository = companyUserRepository;
        this.patientNoteRepository = patientNoteRepository;
    }

    @Override
    @Scheduled(cron = CRON_SCHEDULE_EVERY_2_HOURS_AT_15_MIN)
    @Transactional
    public void importNotes() {
        try {
            List<ClientDTO> clients = clientService.getAllClients();

            for (ClientDTO client : clients) {
                Optional<PatientProfile> optionalPatient =
                    patientProfileRepository.findByOldClientGuidContaining(client.getGuid());

                if (optionalPatient.isEmpty() || !ALLOWED_CLIENT_STATUSES.contains(client.getStatus())) {
                    log.debug("Patient by old guid was not found {}", client.getGuid());
                    return;
                }

                PatientProfile patient = optionalPatient.get();
                NoteRequest request = mapToRequestToClientService(client);
                List<NoteDTO> notes = clientService.getAllNotes(request);

                for (NoteDTO oldNote : notes) {
                    Optional<CompanyUser> optionalUser = companyUserRepository.findByLogin(oldNote.getLoggedUser());
                    CompanyUser user = optionalUser.orElseGet(() -> createAndSaveNewUser(oldNote));

                    //Создает дубликаты? надо сделать механизм сравнения с уже испортированными записями
                    PatientNote patientNote = createNewPatientNote(oldNote, user, patient);
                    patientNoteRepository.save(patientNote);
                }

                log.info("For patient {} were found {} notes", patient.getId(), notes.size());
            }
        } catch (RuntimeException e) {
            log.error("Failed to synchronize data: ", e);
        }
    }

    private static PatientNote createNewPatientNote(NoteDTO note, CompanyUser user, PatientProfile patient) {
        PatientNote patientNote = NoteConverter.toEntity(note);
        patientNote.setCreatedByUser(user);
        patientNote.setLastModifiedByUser(user);
        patientNote.setPatient(patient);
        return patientNote;
    }

    @Transactional
    private CompanyUser createAndSaveNewUser(NoteDTO note) {
        CompanyUser newUser = new CompanyUser();
        newUser.setLogin(note.getLoggedUser());
        return companyUserRepository.save(newUser);
    }

    private static NoteRequest mapToRequestToClientService(ClientDTO client) {
        return new NoteRequest(
            client.getAgency(),
            client.getGuid(),
            IMPORT_FROM_DATE,
            IMPORT_TO_DATE
        );
    }
}
