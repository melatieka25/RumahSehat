package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.PasienModel;

import java.util.List;

public interface PasienService {

    void addPasien(PasienModel pasien);
    List<PasienModel> getListPasien();

}