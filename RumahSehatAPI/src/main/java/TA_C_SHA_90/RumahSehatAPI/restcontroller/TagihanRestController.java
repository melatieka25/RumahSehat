package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;
import TA_C_SHA_90.RumahSehatAPI.service.AppointmentRestService;
import TA_C_SHA_90.RumahSehatAPI.service.PasienRestService;
import TA_C_SHA_90.RumahSehatAPI.service.TagihanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class TagihanRestController {
    @Autowired
    private TagihanRestService tagihanRestService;

    @Autowired
    private PasienRestService pasienRestService;

    @GetMapping(value = "/tagihan")
    private List<TagihanModel> retrieveAllTagihan() { return tagihanRestService.getTagihanList(); }

    @GetMapping(value = "/tagihan/{username}")
    private Map<String, List> retrieveAllTagihanByUsername(@PathVariable("username") String username) {
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
        return jsonMap;
    }

//    @GetMapping(value = "/appointment/detail/{kode}")
//    private AppointmentModel detailAppointment(@PathVariable("kode") String kode) {
//        try {
//            return appointmentRestService.getAppointmentByKode(kode);
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment with Code " + kode + " not found.");
//        }
//    }
}
