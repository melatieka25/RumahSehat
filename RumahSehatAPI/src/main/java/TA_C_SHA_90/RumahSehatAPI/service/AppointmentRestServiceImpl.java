package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.repository.AppointmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class AppointmentRestServiceImpl implements AppointmentRestService{
    @Autowired
    private AppointmentDb appointmentDb;

    @Override
    public AppointmentModel createAppointment(AppointmentModel appointment) {
        return appointmentDb.save(appointment);
    }

    @Override
    public List<AppointmentModel> getAppointmentList() {
        return appointmentDb.findAll();
    }

    @Override
    public AppointmentModel getAppointmentByKode(String kode) {
        Optional<AppointmentModel> appointment = appointmentDb.findByKode(kode);
        if (appointment.isPresent()) {
            return appointment.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}
