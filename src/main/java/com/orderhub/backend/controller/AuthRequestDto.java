package com.orderhub.backend.controller;

import com.orderhub.backend.model.UserModel;

public record AuthRequestDto ( 
    String username,
    String password,
    String email,
    UserModel.Role role) {
}
