package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatWeb.model.TagihanModel;
import TA_C_SHA_90.RumahSehatWeb.repository.AppointmentDb;
import TA_C_SHA_90.RumahSehatWeb.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentDb appointmentDb;

    @Autowired
    private TagihanDb tagihanDb;

    @Override
    public List<AppointmentModel> getListAppointment(){ return appointmentDb.findAll(); }

    @Override
    public AppointmentModel getDetailAppointment(String kode) {
        Optional<AppointmentModel> appointment = appointmentDb.findAllByKode(kode);
        if(appointment.isPresent()) {
            return appointment.get();
        } else return null;
    }

    @Override
    public void setAppointmentDone(AppointmentModel appointment, Integer totalTagihan) {
        appointment.setIsDone(true);

        LocalDateTime time = LocalDateTime.now();
        TagihanModel tagihan = new TagihanModel(null, time, null, false, totalTagihan, appointment);
        tagihanDb.save(tagihan);

        appointment.setTagihan(tagihan);
        appointmentDb.save(appointment);
    }

}
