package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<AppointmentModel> getListAppointment();

    AppointmentModel getDetailAppointment(String kode);
}
