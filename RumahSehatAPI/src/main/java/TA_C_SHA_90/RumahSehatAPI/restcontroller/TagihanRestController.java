package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;
import TA_C_SHA_90.RumahSehatAPI.service.PasienRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TagihanRestController {

    @Autowired
    private PasienRestService pasienRestService;

    @GetMapping(value = "/tagihan/{username}")
    private ResponseEntity retrieveAllTagihanByUsername(Authentication authentication, @PathVariable("username") String username) {
		log.info("Received request at retrieve tagihan endpoint for user " + username);
		
        if(!username.equals(authentication.getName())) {
			log.warn("Authentication failure occurred.");
            return ResponseEntity.status(401).build();
        }
        try {
            PasienModel pasien = pasienRestService.getPasienByUsername(username);
            List<AppointmentModel> listAppointment = pasien.getListAppointment();
            List<TagihanModel> listTagihan = new ArrayList<>();
            for (AppointmentModel appointment : listAppointment){
                if (appointment.getTagihan() != null){
                    listTagihan.add(appointment.getTagihan());
                }
            }
            Map<String, List> jsonMap = new HashMap<>();
            jsonMap.put("listTagihan", listTagihan);
            return ResponseEntity.ok(jsonMap);
        } catch(NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with username " + username + " not found.");
        }

    }
}
