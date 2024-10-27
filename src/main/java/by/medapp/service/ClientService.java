package by.medapp.service;

import by.medapp.core.NoteRequest;
import by.medapp.core.dto.ClientDTO;
import by.medapp.core.dto.NoteDTO;
import by.medapp.service.api.IClientService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static by.medapp.config.JsonMapperConfig.JSON_MAPPER;

@Service
public class ClientService implements IClientService {
    private final RestTemplate restTemplate;

    public ClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<ClientDTO> getAllClients() {
        ResponseEntity<ClientDTO[]> response = restTemplate.getForEntity(
            URI.create("http://localhost:8081/api/clients"),
            ClientDTO[].class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to all client. Status: %s".formatted(response.getStatusCode()));
        }

        return List.of(response.getBody());
    }

    @Override
    public List<NoteDTO> getAllNotes(NoteRequest request) {
        // Peredelat URL + pomieniac na getForEntity
        ResponseEntity<String> response = restTemplate.postForEntity(
            URI.create("http://localhost:8081/api/notes"),
            request,
            String.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to load client notes.\nRequest body: %s\nStatus: %s\nResponse body: %s"
                .formatted(request.toString(), response.getStatusCode(), response.getBody()));
        }

        return JSON_MAPPER.convertValue(response.getBody(), new TypeReference<List<NoteDTO>>() {
        });
    }
}
