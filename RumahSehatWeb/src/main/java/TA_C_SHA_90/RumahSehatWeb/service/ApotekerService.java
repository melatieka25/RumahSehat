package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.ApotekerModel;
import TA_C_SHA_90.RumahSehatWeb.model.DokterModel;

import java.util.List;

public interface ApotekerService {

    void addApoteker(ApotekerModel apoteker);
    List<ApotekerModel> getListApoteker();

}
