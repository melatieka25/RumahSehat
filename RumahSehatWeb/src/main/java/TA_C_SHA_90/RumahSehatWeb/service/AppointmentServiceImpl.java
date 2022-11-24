package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatWeb.repository.AppointmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentDb appointmentDb;

    @Override
    public List<AppointmentModel> getListAppointment(){ return appointmentDb.findAll(); }

    @Override
    public AppointmentModel getDetailAppointment(String kode) {
        Optional<AppointmentModel> appointment = appointmentDb.findAllByKode(kode);
        if(appointment.isPresent()) {
            return appointment.get();
        } else return null;
    }

}
