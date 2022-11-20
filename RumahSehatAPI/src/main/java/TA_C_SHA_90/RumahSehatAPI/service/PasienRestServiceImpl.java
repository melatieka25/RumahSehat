package TA_C_SHA_90.RumahSehatAPI.service;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.repository.PasienDb;

@Service
@Transactional
public class PasienRestServiceImpl implements PasienRestService {
	@Autowired
	private PasienDb pasienDb;
	
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
	public List<PasienModel> getPasienList() {
		return pasienDb.findAll();
	}
	
	@Override
	public PasienModel createPasien(PasienModel pasien) {
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
}