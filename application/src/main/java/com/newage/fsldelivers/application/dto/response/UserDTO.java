package com.newage.fsldelivers.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String customerId;
    private String firstName;
    private String lastName;
}
