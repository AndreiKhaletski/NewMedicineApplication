package by.medapp.service.api;

import by.medapp.core.NoteRequest;
import by.medapp.core.dto.ClientDTO;
import by.medapp.core.dto.NoteDTO;

import java.util.List;

public interface IClientService {
    public List<ClientDTO> getAllClients();
    public List<NoteDTO> getAllNotes(NoteRequest request);
}
