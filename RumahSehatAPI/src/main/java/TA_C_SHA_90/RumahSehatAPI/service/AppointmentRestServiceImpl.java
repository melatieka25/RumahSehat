package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.DokterModel;
import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.model.createAppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.repository.AppointmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class AppointmentRestServiceImpl implements AppointmentRestService{
    @Autowired
    private AppointmentDb appointmentDb;

    @Autowired
    private PasienRestService pasienRestService;

    @Autowired
    private DokterRestService dokterRestService;
    @Override
    public AppointmentModel createAppointment(createAppointmentModel appointment) {
        String str = appointment.getWaktuAwal() + ":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        LocalDateTime dateTimeStart = dateTime.minusHours(1);
        LocalDateTime dateTimeFinish = dateTime.plusHours(1);

        PasienModel pasien = pasienRestService.getPasienByUsername(appointment.getPasien());
        DokterModel dokter = dokterRestService.getDokterByUuid(appointment.getDokter());
        if (appointmentDb.getAllAppointmentWithinInterval(dateTimeStart, dateTimeFinish, appointment.getDokter()).size() > 0) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jadwal bentrok");
        }
        AppointmentModel newAppointment = new AppointmentModel(null, dateTime, false, null, null, pasien, dokter, dokter.getNama(), pasien.getNama());
        return appointmentDb.save(newAppointment);
    }

    @Override
    public List<AppointmentModel> getAppointmentList(String pasien) {
        PasienModel pasienModel = pasienRestService.getPasienByUsername(pasien);
        return appointmentDb.findByPasien(pasienModel);
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
