package com.newage.fsldelivers.application.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class ResetPasswordRequest {
	@NotBlank
	private String email;

	@NotBlank
	private String password;
	
	@NotBlank
	private String resetCode;
}
