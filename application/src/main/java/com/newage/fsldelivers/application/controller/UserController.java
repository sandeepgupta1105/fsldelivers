package com.newage.fsldelivers.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newage.fsldelivers.application.dto.response.ResponseDTO;
import com.newage.fsldelivers.application.dto.response.UserDTO;
import com.newage.fsldelivers.model.User;
import com.newage.fsldelivers.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/users", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(security = { @SecurityRequirement(name = "bearer") })
	@GetMapping
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllUsers() throws JsonProcessingException {

		List<User> users = userService.getAllUsers();

		List<UserDTO> userDTO = users.stream().map(
				s -> new UserDTO(s.getId(), s.getEmail() ,s.getCustomerId(), s.getFirstName(), s.getLastName())
		).collect(Collectors.toList());

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, userDTO, null));

	}
}
