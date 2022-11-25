package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.ObatModel;

import java.util.List;

public interface ObatService {
    List<ObatModel> getListObat();
    ObatModel getObatById(String id);
	void updateObat(ObatModel obat);
}
