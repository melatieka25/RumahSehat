package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatWeb.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<AppointmentModel> getListAppointment();

    AppointmentModel getDetailAppointment(String kode);

    void setAppointmentDone(AppointmentModel appointment, Integer totalTagihan);

    List<AppointmentModel> getListAppointmentDoctor(UserModel user);

}
