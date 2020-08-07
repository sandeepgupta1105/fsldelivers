package com.newage.fsldelivers.service.user.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newage.fsldelivers.enums.ERole;
import com.newage.fsldelivers.enums.EStatus;
import com.newage.fsldelivers.model.Role;
import com.newage.fsldelivers.model.User;
import com.newage.fsldelivers.repository.RoleRepository;
import com.newage.fsldelivers.repository.UserRepository;
import com.newage.fsldelivers.service.exception.ServiceErrorCode;
import com.newage.fsldelivers.service.exception.ServiceErrorMessage;
import com.newage.fsldelivers.service.exception.ServiceException;
import com.newage.fsldelivers.service.user.UserService;
import com.newage.fsldelivers.service.util.AppUtil;
import com.newage.fsldelivers.service.util.EmailUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AppUtil appUtil;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	EmailUtil emailUtil;

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User getUserByCustomerId(String customerId) {
		return userRepository.findByCustomerId(customerId).orElseThrow(
				() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND, ServiceErrorCode.USER_NOT_FOUND));
	}

	@Override
	public User save(User user) {

		user.setCustomerId(appUtil.generateRandomUserName(user));
		user.setStatus(EStatus.ACTIVE);

		Role userRole = roleRepository.findByName(ERole.USER)
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_ROLE_NOT_FOUND,
						ServiceErrorMessage.USER_ROLE_NOT_FOUND));
		user.setRoles(Set.of(userRole));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void resetPassword(String email, String password, String resetCode) {
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND, ServiceErrorMessage.USER_NOT_FOUND));
		if (!resetCode.equals(user.getResetCode())) {
			throw new ServiceException(ServiceErrorCode.RESET_CODE_EXPIRED, ServiceErrorMessage.RESET_CODE_NOT_VALID);
		}
		user.setPassword(passwordEncoder.encode(password));
		user.setResetCode(null);
		user.setResetCodeExpiry(null);
		userRepository.save(user);
	}

	@Override
	public void triggerEmailForResetPassword(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_EMAIL_DOES_NOT_EXIST,
						ServiceErrorMessage.USER_EMAIL_DOES_NOT_EXIST));
		String resetPasswordCode = RandomStringUtils.randomAlphanumeric(20);
		user.setResetCode(resetPasswordCode);
		user.setResetCodeExpiry(appUtil.setResetPasswordExpiryDate());
		emailUtil.sendEmail(email,user);
	}
}
