package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import java.util.*;

import TA_C_SHA_90.RumahSehatAPI.model.JumlahModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import TA_C_SHA_90.RumahSehatAPI.model.ResepModel;
import TA_C_SHA_90.RumahSehatAPI.service.ResepRestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ResepRestController {
    @Autowired
    private ResepRestService resepRestService;

    @GetMapping(value = "/resep/{id}")
    public ResponseEntity retrieveResep(@PathVariable("id") Long id) {
        log.info("Received request at retrieve resep endpoint for resep with id " + id);

        try {
            ResepModel resep =  resepRestService.getResepById(id);
            Map jsonMap = new HashMap();
            jsonMap.put("id", resep.getId());
            jsonMap.put("isDone", resep.getIsDone());
            jsonMap.put("createdAt", resep.getCreatedAt());
            List<Map> listJumlah = new ArrayList();
            for (JumlahModel jumlah : resep.getListJumlah()) {
                Map jumlahObat = new HashMap();
                jumlahObat.put("namaObat", jumlah.getObat().getNama());
                jumlahObat.put("kuantitas", jumlah.getKuantitas());
                listJumlah.add(jumlahObat);
            }
            jsonMap.put("listJumlah", listJumlah);
            jsonMap.put("namaDokter", resep.getAppointment().getDokter().getNama());
            jsonMap.put("namaPasien", resep.getAppointment().getPasien().getNama());
            if (resep.getApoteker() != null) {
                jsonMap.put("namaApoteker", resep.getApoteker().getNama());
            } else {
                jsonMap.put("namaApoteker", "null");
            }
            return ResponseEntity.ok(jsonMap);
        } catch(NoSuchElementException e) {
            log.warn("Failed to retrieve resep, resep id not found: " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resep with id " + id + " not found.");
        }
    }

}
