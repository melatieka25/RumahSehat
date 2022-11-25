package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.ObatModel;
import TA_C_SHA_90.RumahSehatWeb.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ObatServiceImpl implements ObatService {
    @Autowired
    ObatDb obatDb;

    @Override
    public List<ObatModel> getListObat() {
        return obatDb.findAll();
    }

    @Override
    public ObatModel getObatById(String id) {
        Optional<ObatModel> obat = obatDb.findById(id);
        if(obat.isPresent()){
            return obat.get();
        }
        return null;
    }
}
