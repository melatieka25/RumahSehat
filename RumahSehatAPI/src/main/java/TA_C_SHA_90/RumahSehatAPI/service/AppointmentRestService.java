package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.createAppointmentModel;

import java.util.List;

public interface AppointmentRestService {
    AppointmentModel createAppointment(createAppointmentModel appointment);
    List<AppointmentModel> getAppointmentList();
    AppointmentModel getAppointmentByKode(String kode);
}
