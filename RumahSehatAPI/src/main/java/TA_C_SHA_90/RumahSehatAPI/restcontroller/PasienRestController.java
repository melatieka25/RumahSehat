package TA_C_SHA_90.RumahSehatAPI.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.service.PasienRestService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class PasienRestController {
	@Autowired
	private PasienRestService pasienRestService;

	// Retrieve
	@GetMapping(value = "/pasien/{uuid}")
	private PasienModel retrievePasien(@PathVariable("uuid") String uuid) {
		try {
			return pasienRestService.getPasienByUuid(uuid);
		} catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with UUID " + uuid + " not found.");
		}
	}
	
	// Retrieve all
	@GetMapping(value = "/pasien")
	private List<PasienModel> retrievePasien() {
		return pasienRestService.getPasienList();
	}
	
	// Post 
	@PostMapping(value = "/pasien/new")
	private PasienModel createPasien(@Valid @RequestBody PasienModel pasien, BindingResult bindingResult) {
		if(bindingResult.hasFieldErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field.");
		} else {
			return pasienRestService.createPasien(pasien);
		}
	}
	
	// Delete
	@DeleteMapping(value = "/pasien/{uuid}")
	private ResponseEntity deletePasien(@PathVariable("uuid") String uuid) {
		try {
			pasienRestService.deletePasien(uuid);
			return ResponseEntity.ok("Pasien with UUID " + uuid + " has been deleted successfully");
		} catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with UUID " + uuid + " not found.");
		}
	}
	
	// Update
	@PutMapping(value = "/pasien/{uuid}")
	private PasienModel updatePasien(@PathVariable("uuid") String uuid, @RequestBody PasienModel pasien) {
		try {
			return pasienRestService.updatePasien(uuid, pasien);
		} catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien with UUID " + uuid + " not found.");
		}
	}
}