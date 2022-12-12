package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;

import javax.validation.Valid;

import TA_C_SHA_90.RumahSehatAPI.model.LoginModel;
import TA_C_SHA_90.RumahSehatAPI.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;

import lombok.extern.slf4j.Slf4j;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.service.PasienRestService;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PasienRestController {
    @Autowired
    private PasienRestService pasienRestService;

    // Retrieve
    @GetMapping(value = "/pasien/{uuid}")
    private PasienModel retrievePasien(@PathVariable("uuid") String uuid) {
        log.info("Received request at retrieve pasien endpoint for user with UUID " + uuid);
        try {
            return pasienRestService.getPasienByUuid(uuid);
        } catch(NoSuchElementException e) {
            log.warn("Failed to fetch user with UIUD " + uuid);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with UUID " + uuid + " not found.");
        }
    }
    
    // Retrieve all
    @GetMapping(value = "/pasien")
    private List<PasienModel> retrieveAllPasien() {
        log.info("Received request at retrieve all pasien endpoint");
        return pasienRestService.getPasienList();
    }
    
    // Post 
    @PostMapping(value = "/pasien/new")
    private PasienModel createPasien(@Valid @RequestBody PasienModel pasien, BindingResult bindingResult) {
        log.info("Received request at create new pasien endpoint");
        if(bindingResult.hasFieldErrors()) {
            log.warn("Failed to create new pasien");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field.");
        } else {
            return pasienRestService.createPasien(pasien);
        }
    }
    
    // Delete
    @DeleteMapping(value = "/pasien/{uuid}")
    private ResponseEntity deletePasien(@PathVariable("uuid") String uuid) {
        log.info("Received request at delete pasien endpoint");
        try {
            pasienRestService.deletePasien(uuid);
            return ResponseEntity.ok("Pasien with UUID " + uuid + " has been deleted successfully");
        } catch(NoSuchElementException e) {
            log.warn("Failed to delete new pasien, UUID not found: " + uuid);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with UUID " + uuid + " not found.");
        }
    }
    
    // Update
    @PutMapping(value = "/pasien/{uuid}")
    private PasienModel updatePasien(@PathVariable("uuid") String uuid, @RequestBody PasienModel pasien) {
        log.info("Received request at update pasien endpoint");
        try {
            return pasienRestService.updatePasien(uuid, pasien);
        } catch(NoSuchElementException e) {
            log.warn("Failed to update pasien, UUID not found: " + uuid);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with UUID " + uuid + " not found.");
        }
    }
    
    @PostMapping(value = "/pasien/saldo")
    private ResponseEntity updateSaldo(Authentication authentication, @RequestBody Map<String, String> params) {
        log.info("Received request at update pasien saldo endpoint");
        // Only allow to access one's own saldo
        String username = params.get("username");
        String saldo = params.get("saldo");
        if(!username.equals(authentication.getName())) {
            log.warn("Authentication error when updating saldo");
            return ResponseEntity.status(401).build();
        }
        try {
            PasienModel pasien = pasienRestService.getPasienByUsername(username);
            pasien.setSaldo(pasien.getSaldo() + Integer.valueOf(saldo));
            pasienRestService.updatePasien(pasien.getUuid(), pasien);
            return ResponseEntity.ok("Saldo for pasien " + username + " has been updated successfully");
        } catch(NoSuchElementException e) {
            log.warn("Failed to update pasien saldo, username not found: " + username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with username " + username + " not found.");
        }
    }
    @GetMapping(value = "/pasien/profil/{username}")
    private ResponseEntity retrieveProfil(Authentication authentication, @PathVariable("username") String username) {
        log.info("Received request at profil pasien endpoint");
        if(!username.equals(authentication.getName())) {
            log.warn("Authentication error occurred");
            return ResponseEntity.status(401).build();
        }
        try {
            PasienModel pasien = pasienRestService.getPasienByUsername(username);
            return ResponseEntity.ok(pasien);
        } catch (NoSuchElementException e) {
            log.warn("Failed to fetch user profile, username not found: " + username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with username " + username + " not found.");
        }
    }
}