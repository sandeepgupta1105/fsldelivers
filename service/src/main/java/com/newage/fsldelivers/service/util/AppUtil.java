package com.newage.fsldelivers.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newage.fsldelivers.model.User;
import com.newage.fsldelivers.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AppUtil {

	@Autowired
	UserRepository userRepository;

	public String generateRandomUserName(User user) {
		String generatedString = RandomStringUtils.randomNumeric(6);
		String random = "FSL" + generatedString + user.getFirstName().substring(0, 1).toUpperCase();
		Boolean existsByCustomerId = userRepository.existsByCustomerId(random.strip());
		if (existsByCustomerId.equals(Boolean.FALSE)) {
			return random.strip();
		} else {
			return generateRandomUserName(user);
		}
	}

	public java.util.Date setResetPasswordExpiryDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		System.out.println("Current Date: " + sdf.format(cal.getTime()));
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String newDate = sdf.format(cal.getTime());
		Date expiryDate = null;
		try {
			expiryDate = sdf.parse(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expiryDate;
	}
}
