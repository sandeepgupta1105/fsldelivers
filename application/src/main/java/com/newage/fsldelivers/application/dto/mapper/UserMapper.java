package com.newage.fsldelivers.application.dto.mapper;

import com.newage.fsldelivers.application.dto.request.SignupRequest;
import com.newage.fsldelivers.application.dto.response.UserDTO;
import com.newage.fsldelivers.model.User;
import com.newage.fsldelivers.model.UserContact;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User covertToEntity(SignupRequest signupRequest) {
        User user =  modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        UserContact userContact = new UserContact();
        userContact.setAddress(signupRequest.getAddress());
        userContact.setCity(signupRequest.getCity());
        userContact.setCountryId(signupRequest.getCountryId());
        userContact.setPhoneNumber(signupRequest.getPhoneNumber());
        userContact.setUser(user);
        userContact.setPostalcode(signupRequest.getPostalcode());
        user.setUserContacts(List.of(userContact));
        return user;
    }

    public UserDTO convertToDTO(User user) {

        return new UserDTO(user.getId(),user.getEmail(), user.getCustomerId(), user.getFirstName(), user.getLastName());
    }
}
