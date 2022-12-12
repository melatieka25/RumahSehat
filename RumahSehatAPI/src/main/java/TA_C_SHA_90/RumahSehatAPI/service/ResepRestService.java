package TA_C_SHA_90.RumahSehatAPI.service;

import java.util.List;
import TA_C_SHA_90.RumahSehatAPI.model.ResepModel;

public interface ResepRestService {
    ResepModel getResepById(Long id);

    List<ResepModel> getResepList();
}
