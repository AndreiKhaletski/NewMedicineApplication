package by.medapp.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientDTO {
    private String agency;
    private String guid;
    private String firstName;
    private String lastName;
    private int status;
    private String dob;
    private String createdDateTime;
}
