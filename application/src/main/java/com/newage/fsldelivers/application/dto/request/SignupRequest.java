package com.newage.fsldelivers.application.dto.request;

import com.newage.fsldelivers.enums.EIdentificationType;
import com.newage.fsldelivers.enums.ERole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SignupRequest {

    @NotBlank
    @Column(name = "email", length = 100)
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date dob;

    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;

    @NotNull
    private EIdentificationType identificationType;

    @NotBlank
    @Size(min = 5, max = 50)
    private String identificationNumber;

    @NotNull
    private ERole role;

    @NotBlank
    @Size(min = 1, max = 100)
    private String nationality;

    @NotBlank
    @Size(min = 1, max = 500)
    private String address;

    @NotBlank
    @Size(min = 1, max = 100)
    private String city;

    @NotNull
    private Integer postalcode;

    @NotNull
    private Long countryId;

    @NotBlank
    @Size(min = 1, max = 10)
    private String phoneNumber;

}
