package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.UserModel;
import TA_C_SHA_90.RumahSehatWeb.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import TA_C_SHA_90.RumahSehatWeb.model.DokterModel;
import TA_C_SHA_90.RumahSehatWeb.model.UserModel;
import TA_C_SHA_90.RumahSehatWeb.repository.DokterDb;
import TA_C_SHA_90.RumahSehatWeb.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.List;

import static TA_C_SHA_90.RumahSehatWeb.PasswordManager.encrypt;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDb userDb;

    @Override
    public List<UserModel> getListUser() {
        return userDb.findAll();
    }

    @Override
    public UserModel getUserByEmail(String email) {
        Optional<UserModel> user = userDb.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else return null;
    }

    @Override
    public UserModel getUserByUsername(String username) {

        Optional<UserModel> user = userDb.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else return null;
    }
}
