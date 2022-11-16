package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.ApotekerModel;
import TA_C_SHA_90.RumahSehatWeb.model.DokterModel;
import TA_C_SHA_90.RumahSehatWeb.repository.ApotekerDb;
import TA_C_SHA_90.RumahSehatWeb.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ApotekerServiceImpl implements ApotekerService {
    @Autowired
    ApotekerDb apotekerDb;

    @Override
    public void addApoteker(ApotekerModel apoteker) {
        apotekerDb.save(apoteker);
    }

    @Override
    public List<ApotekerModel> getListApoteker() {
        return apotekerDb.findAll();
    }
}
