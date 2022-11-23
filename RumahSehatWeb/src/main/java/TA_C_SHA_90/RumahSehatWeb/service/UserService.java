package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> getListUser();

    UserModel getUserByUsername(String username);

    UserModel addUser(UserModel user);
    public String encrypt (String password);
}
