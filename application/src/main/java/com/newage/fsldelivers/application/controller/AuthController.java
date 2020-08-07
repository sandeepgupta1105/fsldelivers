package com.newage.fsldelivers.application.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newage.fsldelivers.application.dto.mapper.UserMapper;
import com.newage.fsldelivers.application.dto.request.EmailCheckRequest;
import com.newage.fsldelivers.application.dto.request.EmailTriggerRequest;
import com.newage.fsldelivers.application.dto.request.LoginRequest;
import com.newage.fsldelivers.application.dto.request.ResetPasswordRequest;
import com.newage.fsldelivers.application.dto.request.SignupRequest;
import com.newage.fsldelivers.application.dto.response.ExistsResponseDTO;
import com.newage.fsldelivers.application.dto.response.JWT;
import com.newage.fsldelivers.application.dto.response.ResponseDTO;
import com.newage.fsldelivers.application.dto.response.UserDTO;
import com.newage.fsldelivers.application.security.AuthUtil;
import com.newage.fsldelivers.model.User;
import com.newage.fsldelivers.service.exception.ServiceErrorCode;
import com.newage.fsldelivers.service.exception.ServiceErrorMessage;
import com.newage.fsldelivers.service.exception.ServiceException;
import com.newage.fsldelivers.service.user.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1/auth", produces = {
		MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

	@Autowired
	AuthUtil authUtil;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserMapper userMapper;

	@PostMapping("/signin")
	public ResponseEntity<ResponseDTO> signIn(@Valid @RequestBody LoginRequest loginRequest) {

		JWT jwtResponse = authUtil.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, jwtResponse, null));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		Boolean existsByEmail = userService.existsByEmail(signUpRequest.getEmail());

		if(existsByEmail.equals(Boolean.TRUE)){
			throw new ServiceException(ServiceErrorCode.USER_EMAIL_ALREADY_EXIST,
					ServiceErrorMessage.USER_EMAIL_ALREADY_EXIST);
		}

		User user = userMapper.covertToEntity(signUpRequest);

		UserDTO userDTO = userMapper.convertToDTO(userService.save(user));

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, userDTO, null));

	}

	@PostMapping("/checkemail")
	public ResponseEntity<?> validateEmailForSignup( @RequestBody EmailCheckRequest emailCheckRequest) {
		Boolean result = userService.existsByEmail(emailCheckRequest.getEmail());
		return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, new ExistsResponseDTO(result), null));
	}
	
	@PostMapping("/resetpasswordemail")
	public ResponseEntity<?> triggerEmailForResetPassword(@RequestBody EmailTriggerRequest emailTriggerRequest) {
		userService.triggerEmailForResetPassword(emailTriggerRequest.getEmail());
		return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, null, null));
	}

	@PostMapping("/resetpassword")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
		userService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getPassword(),resetPasswordRequest.getResetCode());
		return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, null, null));
	}

}
