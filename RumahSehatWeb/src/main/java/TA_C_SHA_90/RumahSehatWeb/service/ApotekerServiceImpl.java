package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.ApotekerModel;
import TA_C_SHA_90.RumahSehatWeb.repository.ApotekerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static TA_C_SHA_90.RumahSehatWeb.PasswordManager.encrypt;

@Service
@Transactional
public class ApotekerServiceImpl implements ApotekerService {
    @Autowired
    ApotekerDb apotekerDb;

    @Override
    public void addApoteker(ApotekerModel apoteker) {
        String pass = encrypt(apoteker.getPassword());
        apoteker.setPassword(pass);
        apotekerDb.save(apoteker);
    }

    @Override
    public List<ApotekerModel> getListApoteker() {
        return apotekerDb.findAll();
    }
}
