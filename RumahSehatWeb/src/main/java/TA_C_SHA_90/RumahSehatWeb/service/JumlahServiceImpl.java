package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.JumlahModel;
import TA_C_SHA_90.RumahSehatWeb.repository.JumlahDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class JumlahServiceImpl implements JumlahService {
    @Autowired
    JumlahDb jumlahDb;

    @Override
    public void addJumlah(JumlahModel jumlah) {
        jumlahDb.save(jumlah);
    }

    @Override
    public List<JumlahModel> getListJumlah() {
        return jumlahDb.findAll();
    }
}
