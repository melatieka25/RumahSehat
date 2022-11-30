package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.DokterModel;
import TA_C_SHA_90.RumahSehatAPI.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DokterRestServiceImpl implements DokterRestService {
    @Autowired
    private DokterDb dokterDb;

    @Override
    public List<DokterModel> getAllDokter() {
        return dokterDb.findAll();
    }

    @Override
    public DokterModel getDokterByUuid(String uuid) {
        return dokterDb.findByUuid(uuid);
    }
}
