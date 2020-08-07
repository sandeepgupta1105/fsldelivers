package com.newage.fsldelivers.service.user;

import java.util.List;

import com.newage.fsldelivers.model.User;

public interface UserService {

	Boolean existsByEmail(String email);

	User getUserByCustomerId(String customerId);

	User save(User user);

	List<User> getAllUsers();

	void resetPassword(String email, String password, String confirmPassword);

	void triggerEmailForResetPassword(String email);

}
