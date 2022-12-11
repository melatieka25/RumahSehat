package TA_C_SHA_90.RumahSehatAPI.service;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TA_C_SHA_90.RumahSehatAPI.model.ResepModel;
import TA_C_SHA_90.RumahSehatAPI.repository.ResepDb;

@Service
@Transactional
public class ResepRestServiceImpl implements ResepRestService {
    @Autowired
    private ResepDb resepDb;

    @Override
    public ResepModel getResepById(Long id) {
        Optional<ResepModel> resep = resepDb.findById(id);
        if(resep.isPresent()) {
            return resep.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<ResepModel> getResepList() {
        return resepDb.findAll();
    }
}
