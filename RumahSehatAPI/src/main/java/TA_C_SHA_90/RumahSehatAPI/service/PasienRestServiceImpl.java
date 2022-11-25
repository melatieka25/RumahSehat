package TA_C_SHA_90.RumahSehatAPI.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import TA_C_SHA_90.RumahSehatAPI.model.UserModel;
import TA_C_SHA_90.RumahSehatAPI.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.repository.PasienDb;

import static TA_C_SHA_90.RumahSehatAPI.PasswordManager.encrypt;

@Service
@Transactional
public class PasienRestServiceImpl implements PasienRestService {
	@Autowired
	private PasienDb pasienDb;

	@Autowired
	private UserDb userDb;
	
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
		String pass = encrypt(pasien.getPassword());
		pasien.setPassword(pass);
		return pasienDb.save(pasien);
	}
	
	@Override
	public void deletePasien(String uuid) {
		PasienModel pasien = getPasienByUuid(uuid);
		pasienDb.delete(pasien);
	}
	
	@Override
	public PasienModel updatePasien(String uuid, PasienModel updatedPasien) {
		PasienModel pasien = getPasienByUuid(uuid);
		pasien.setNama(updatedPasien.getNama());
		pasien.setRole(updatedPasien.getRole());
		pasien.setUsername(updatedPasien.getUsername());
		pasien.setPassword(updatedPasien.getPassword());
		pasien.setEmail(updatedPasien.getEmail());
		pasien.setSaldo(updatedPasien.getSaldo());
		pasien.setUmur(updatedPasien.getUmur());
		return pasienDb.save(pasien);
	}

	@Override
	public List<String> getUserEmailList() {
		List<String> listEmail = new ArrayList<>();
		List<UserModel> listUser = userDb.findAll();
		for (UserModel user : listUser){
			listEmail.add(user.getEmail());
		}
		return listEmail;
	}

	@Override
	public List<String> getUserUsernameList() {
		List<String> listUsername = new ArrayList<>();
		List<UserModel> listUser = userDb.findAll();
		for (UserModel user : listUser){
			listUsername.add(user.getUsername());
		}
		return listUsername;
	}
}