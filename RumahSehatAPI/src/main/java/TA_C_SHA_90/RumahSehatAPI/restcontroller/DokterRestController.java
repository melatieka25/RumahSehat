package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import TA_C_SHA_90.RumahSehatAPI.model.DokterModel;
import TA_C_SHA_90.RumahSehatAPI.service.DokterRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DokterRestController {
    @Autowired
    private DokterRestService dokterRestService;

    @GetMapping("/dokter")
    private List<DokterModel> getAllDokter() { return dokterRestService.getAllDokter(); }
}
