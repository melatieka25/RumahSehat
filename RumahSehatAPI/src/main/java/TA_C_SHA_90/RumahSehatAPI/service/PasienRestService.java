package TA_C_SHA_90.RumahSehatAPI.service;

import java.util.List;
import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;

public interface PasienRestService {
	PasienModel getPasienByUuid(String uuid);
	List<PasienModel> getPasienList();
	PasienModel createPasien(PasienModel pasien);
	void deletePasien(String uuid);
	PasienModel updatePasien(String uuid, PasienModel updatedPasien);
}