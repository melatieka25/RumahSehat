package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.DokterModel;

import java.util.List;

public interface DokterRestService {
    List<DokterModel> getAllDokter();

    DokterModel getDokterByUuid(String uuid);
}
