package com.newage.fsldelivers.application.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "error")
public class ResponseError {

    private String code;
    private List<String> message;

}