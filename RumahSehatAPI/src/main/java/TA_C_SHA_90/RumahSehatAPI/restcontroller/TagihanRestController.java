package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;
import TA_C_SHA_90.RumahSehatAPI.service.PasienRestService;
import TA_C_SHA_90.RumahSehatAPI.service.TagihanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TagihanRestController {

    @Autowired
    private PasienRestService pasienRestService;

    @Autowired
    private TagihanRestService tagihanRestService;

    @GetMapping(value = "/tagihan/{username}")
    public ResponseEntity retrieveAllTagihanByUsername(Authentication authentication, @PathVariable("username") String username) {
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
            log.warn("Failed to retrieve tagihan, pasien username not found: " + username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with username " + username + " not found.");
        }

    }

    @GetMapping(value = "/tagihan/{username}/bayar/{kode}")
    private ResponseEntity payTagihanByKode(Authentication authentication, @PathVariable("username") String username, @PathVariable("kode") String kode) {
        log.info("Received request at pay tagihan endpoint for user " + username);

        if(!username.equals(authentication.getName())) {
            log.warn("Authentication failure occurred.");
            return ResponseEntity.status(401).build();
        }
        try {
            PasienModel pasien = pasienRestService.getPasienByUsername(username);
            TagihanModel tagihan = tagihanRestService.getTagihanByKode(kode);
            Boolean status = tagihanRestService.payTagihan(tagihan, pasien);
            Map<String, Boolean> jsonMap = new HashMap<>();
            jsonMap.put("statusPembayaran", status);
            return ResponseEntity.ok(jsonMap);
        } catch(NoSuchElementException e) {
            log.warn("Failed to update tagihan, tagihan code not found: " + kode);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tagihan with code " + kode + " not found.");
        }

    }
}
