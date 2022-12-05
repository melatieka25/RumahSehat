package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.UserModel;
import TA_C_SHA_90.RumahSehatWeb.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDb userDb;

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
        }
        else return null;
    }


}
