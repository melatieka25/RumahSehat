package TA_C_SHA_90.RumahSehatAPI.service;

import javax.transaction.Transactional;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;
import TA_C_SHA_90.RumahSehatAPI.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TagihanRestServiceImpl implements TagihanRestService {
    @Autowired
    private TagihanDb tagihanDb;

    @Override
    public TagihanModel getTagihanByKode(String kode) {
        Optional<TagihanModel> tagihan = tagihanDb.findByKode(kode);
        if(tagihan.isPresent()) {
            return tagihan.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Boolean payTagihan(TagihanModel tagihan, PasienModel pasien) {
        if (pasien.getSaldo() >= tagihan.getJumlahTagihan()) {
            tagihan.setIsPaid(true);

            LocalDateTime now = LocalDateTime.now();
            tagihan.setTanggalBayar(now);

            pasien.setSaldo(pasien.getSaldo() - tagihan.getJumlahTagihan());
            return true;
        } else {
            return false;
        }
    }
}
