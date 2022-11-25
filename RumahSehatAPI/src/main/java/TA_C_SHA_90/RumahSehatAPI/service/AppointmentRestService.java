package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;

import java.util.List;

public interface AppointmentRestService {
    AppointmentModel createAppointment(AppointmentModel appointment);
    List<AppointmentModel> getAppointmentList();
    AppointmentModel getAppointmentByKode(String kode);
}
