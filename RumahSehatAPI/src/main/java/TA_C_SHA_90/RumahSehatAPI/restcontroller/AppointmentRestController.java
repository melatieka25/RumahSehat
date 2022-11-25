package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.service.AppointmentRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class AppointmentRestController {
    @Autowired
    private AppointmentRestService appointmentRestService;

    @PostMapping(value = "/appointment/create")
    private AppointmentModel createAppointment(@Valid @RequestBody AppointmentModel appointment, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field! ");
        } else {
            return appointmentRestService.createAppointment(appointment);
        }
    }

    @GetMapping(value = "/appointment")
    private List<AppointmentModel> retrieveAllAppointment() { return appointmentRestService.getAppointmentList(); }

    @GetMapping(value = "/appointment/detail/{kode}")
    private AppointmentModel detailAppointment(@PathVariable("kode") String kode) {
        try {
            return appointmentRestService.getAppointmentByKode(kode);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment with Code " + kode + " not found.");
        }
    }
}
