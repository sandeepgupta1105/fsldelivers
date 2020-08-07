package com.newage.fsldelivers.application.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.List;

@Data
@JsonTypeInfo(include= JsonTypeInfo.As.EXTERNAL_PROPERTY, use= JsonTypeInfo.Id.NAME)
@JsonRootName(value = "JWT")
public class JWT {

    private Long id;
    private String token;
    private String type = "Bearer";
    private String customerId;
    private String email;
    private List<String> roles;

    public JWT(String accessToken, Long id, String customerId, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.customerId = customerId;
        this.email = email;
        this.roles = roles;
    }
}
