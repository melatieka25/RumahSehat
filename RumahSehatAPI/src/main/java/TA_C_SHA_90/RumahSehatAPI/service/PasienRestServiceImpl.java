package TA_C_SHA_90.RumahSehatAPI.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import TA_C_SHA_90.RumahSehatAPI.model.UserModel;
import TA_C_SHA_90.RumahSehatAPI.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.repository.PasienDb;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class PasienRestServiceImpl implements PasienRestService {
	@Autowired
	private PasienDb pasienDb;

	@Autowired
	private UserDb userDb;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Override
	public PasienModel getPasienByUuid(String uuid) {
		Optional<PasienModel> pasien = pasienDb.findByUuid(uuid);
		if(pasien.isPresent()) {
			return pasien.get();
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public PasienModel getPasienByUsername(String username) {
		Optional<PasienModel> pasien = pasienDb.findByUsername(username);
		if(pasien.isPresent()) {
			return pasien.get();
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public List<PasienModel> getPasienList() {
		return pasienDb.findAll();
	}
	
	@Override
	public PasienModel createPasien(PasienModel pasien) {
		UserModel userSameEmail;
		UserModel userSameUsername;
		try{
			userSameEmail = userDb.findByEmail(pasien.getEmail()).get();
		} catch (NoSuchElementException e){
			userSameEmail = null;
		}

		try{
			userSameUsername = userDb.findByUsername(pasien.getUsername()).get();
		} catch (NoSuchElementException e){
			userSameUsername = null;
		}

		if (userSameEmail != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Akun dengan email yang sama sudah pernah dibuat!");
		} else if (userSameUsername != null){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Akun dengan username yang sama sudah pernah dibuat!");
		} else {
			String pass = jwtUserDetailsService.encrypt(pasien.getPassword());
			pasien.setPassword(pass);
			return pasienDb.save(pasien);
		}
	}
	
	@Override
	public void deletePasien(String uuid) {
		PasienModel pasien = getPasienByUuid(uuid);
		pasienDb.delete(pasien);
	}
	
	@Override
	public PasienModel updatePasien(String uuid, PasienModel updatedPasien) {
		PasienModel pasien = getPasienByUuid(uuid);
		pasien.setSaldo(updatedPasien.getSaldo());
		return pasienDb.save(pasien);
	}
}