package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.UserModel;


public interface UserService {

    UserModel getUserByUsername(String username);

    UserModel getUserByEmail(String email);
}
