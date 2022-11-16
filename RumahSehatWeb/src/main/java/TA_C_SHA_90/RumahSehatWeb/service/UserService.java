package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.UserModel;

public interface UserService {

    UserModel getUserByEmail(String email);
    UserModel getUserByUsername(String username);
}
