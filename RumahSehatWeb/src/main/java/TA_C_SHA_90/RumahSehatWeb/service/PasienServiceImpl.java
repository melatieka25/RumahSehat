package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.ApotekerModel;
import TA_C_SHA_90.RumahSehatWeb.model.PasienModel;
import TA_C_SHA_90.RumahSehatWeb.repository.ApotekerDb;
import TA_C_SHA_90.RumahSehatWeb.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PasienServiceImpl implements PasienService {
    @Autowired
    PasienDb pasienDb;

    @Override
    public void addPasien(PasienModel pasien) {
        pasienDb.save(pasien);
    }

    @Override
    public List<PasienModel> getListPasien() {
        return pasienDb.findAll();
    }

}
