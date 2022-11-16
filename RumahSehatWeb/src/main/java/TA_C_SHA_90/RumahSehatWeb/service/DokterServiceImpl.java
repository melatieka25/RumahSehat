package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.DokterModel;
import TA_C_SHA_90.RumahSehatWeb.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DokterServiceImpl implements DokterService {
    @Autowired
    DokterDb dokterDb;

    @Override
    public void addDokter(DokterModel dokter) {
        dokterDb.save(dokter);
    }

    @Override
    public List<DokterModel> getListDokter() {
        return dokterDb.findAll();
    }
}
