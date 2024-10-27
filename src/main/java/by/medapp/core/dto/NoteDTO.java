package by.medapp.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NoteDTO {
    private String comments;
    private String guid;
    private String modifiedDateTime;
    private String clientGuid;
    private String datetime;
    private String loggedUser;
    private String createdDateTime;
}
