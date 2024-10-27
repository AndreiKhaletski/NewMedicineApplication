package by.medapp.controller;

import by.medapp.core.NoteRequest;
import by.medapp.core.dto.NoteDTO;
import by.medapp.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    private final ClientService clientService;

    public NoteController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/notes")
    public List<NoteDTO> getNotes(@RequestBody NoteRequest request) {
        return clientService.getAllNotes(request);
    }
}

