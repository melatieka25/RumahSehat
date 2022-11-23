package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.DokterModel;

import java.util.List;

public interface DokterService {

    void addDokter(DokterModel dokter);
    List<DokterModel> getListDokter();

}
