package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.ResepModel;

import java.util.List;

public interface ResepService {
    void addResep(ResepModel resep);
    List<ResepModel> getListResep();
    ResepModel getResepById(Long id);
    Boolean confirmResep(ResepModel resep);
}
