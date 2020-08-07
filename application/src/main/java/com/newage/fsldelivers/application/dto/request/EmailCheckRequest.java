package com.newage.fsldelivers.application.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmailCheckRequest {

	@NotBlank
	private String email;

}
