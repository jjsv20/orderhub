package com.orderhub.backend.service;

import java.util.Optional;

import com.orderhub.backend.model.UserModel;
import com.orderhub.backend.model.UserModel.Role;

public interface AuthService {

    String login(String email, String password);

     String signUp(String username, String password, String email, Role role);
    //String signUp(AuthRequestDto requestDto);

    String verifyToken(String token);
  
    Optional<UserModel> findByEmail(String email);

    void saveUserVerificationToken(UserModel theUser, String verificationToken);

    String validateToken(String theToken);

}