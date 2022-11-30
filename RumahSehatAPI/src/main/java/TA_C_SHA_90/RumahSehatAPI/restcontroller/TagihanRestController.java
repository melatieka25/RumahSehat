package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;
import TA_C_SHA_90.RumahSehatAPI.service.AppointmentRestService;
import TA_C_SHA_90.RumahSehatAPI.service.TagihanRestService;
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
public class TagihanRestController {
    @Autowired
    private TagihanRestService tagihanRestService;

    @PostMapping(value = "/tagihan/create")
    private TagihanModel createTagihan(@Valid @RequestBody TagihanModel tagihan, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field! ");
        } else {
            return tagihanRestService.createTagihan(tagihan);
        }
    }

    @GetMapping(value = "/tagihan")
    private List<TagihanModel> retrieveAllTagihan() { return tagihanRestService.getTagihanList(); }

//    @GetMapping(value = "/appointment/detail/{kode}")
//    private AppointmentModel detailAppointment(@PathVariable("kode") String kode) {
//        try {
//            return appointmentRestService.getAppointmentByKode(kode);
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment with Code " + kode + " not found.");
//        }
//    }
}
