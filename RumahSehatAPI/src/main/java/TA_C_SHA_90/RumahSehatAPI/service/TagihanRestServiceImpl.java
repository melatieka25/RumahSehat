package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;
import TA_C_SHA_90.RumahSehatAPI.repository.AppointmentDb;
import TA_C_SHA_90.RumahSehatAPI.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TagihanRestServiceImpl implements TagihanRestService{
    @Autowired
    private TagihanDb tagihanDb;

    @Override
    public TagihanModel createTagihan(TagihanModel tagihan) {
        return tagihanDb.save(tagihan);
    }

    @Override
    public List<TagihanModel> getTagihanList() {
        return tagihanDb.findAll();
    }

    @Override
    public TagihanModel getTagihanByAppointment(AppointmentModel appointment) {
        Optional<TagihanModel> tagihan = tagihanDb.findByAppointment(appointment);
        if (tagihan.isPresent()) {
            return tagihan.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}
