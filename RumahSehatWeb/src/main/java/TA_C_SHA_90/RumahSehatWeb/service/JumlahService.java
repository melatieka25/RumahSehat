package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.JumlahModel;

import java.util.List;

public interface JumlahService {
    void addJumlah(JumlahModel jumlah);
    List<JumlahModel> getListJumlah();
}
