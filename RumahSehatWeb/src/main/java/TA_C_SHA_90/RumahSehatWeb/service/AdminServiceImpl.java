package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.AdminModel;
import TA_C_SHA_90.RumahSehatWeb.model.ApotekerModel;
import TA_C_SHA_90.RumahSehatWeb.repository.AdminDb;
import TA_C_SHA_90.RumahSehatWeb.repository.ApotekerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static TA_C_SHA_90.RumahSehatWeb.PasswordManager.encrypt;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDb adminDb;

    @Override
    public void addAdmin(AdminModel admin) {
        String pass = encrypt(admin.getPassword());
        admin.setPassword(pass);
        adminDb.save(admin);
    }
}
