package by.medapp.core;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NoteRequest {
    private String agency;
    private String dateFrom;
    private String dateTo;
    private String clientGuid;
}
