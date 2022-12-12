package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.ResepModel;
import TA_C_SHA_90.RumahSehatAPI.model.createAppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.service.AppointmentRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AppointmentRestController {
    @Autowired
    private AppointmentRestService appointmentRestService;

    @PostMapping(value = "/appointment/create")
    private ResponseEntity createAppointment(Authentication authentication, @Valid @RequestBody createAppointmentModel appointment, BindingResult bindingResult) {
        log.info("Received request at create new appointment endpoint");
        if(!appointment.getPasien().equals(authentication.getName())) {
            log.warn("Authentication failure occurred.");
            return ResponseEntity.status(401).build();
        }
        if (bindingResult.hasFieldErrors()) {
            log.warn("Failed to create new appointment");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field! ");
        } else {
            AppointmentModel appointmentModel = appointmentRestService.createAppointment(appointment);
            return ResponseEntity.ok(appointmentModel);
        }
    }

    @GetMapping(value = "/appointment/{pasien}")
    private ResponseEntity retrieveAllAppointment(Authentication authentication, @PathVariable String pasien) {
        log.info("Received request at retrieve appointment endpoint for pasien with username " + pasien);
        if(!pasien.equals(authentication.getName())) {
            log.warn("Authentication failure occurred.");
            return ResponseEntity.status(401).build();
        }
        try {
            List<AppointmentModel> listAppointment = appointmentRestService.getAppointmentList(pasien);
            for (int i = 0; i < listAppointment.size(); i++) {
                String namaDokter = listAppointment.get(i).getDokter().getNama();
                listAppointment.get(i).setNamaDokter(namaDokter);
                String namaPasien = listAppointment.get(i).getPasien().getNama();
                listAppointment.get(i).setNamaPasien(namaPasien);
            }

            Map<String, List> jsonMap = new HashMap<>();
            jsonMap.put("listAppointment", listAppointment);
            return ResponseEntity.ok(jsonMap);
        } catch (NoSuchElementException e) {
            log.warn("Failed to retrieve appointment, pasien username not found: " + pasien);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with Username " + pasien + " not found.");
        }
    }
    
}
