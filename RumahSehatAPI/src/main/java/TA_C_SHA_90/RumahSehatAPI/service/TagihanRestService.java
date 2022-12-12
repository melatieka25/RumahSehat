package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;

public interface TagihanRestService {
    TagihanModel getTagihanByKode(String kode);
    Boolean payTagihan(TagihanModel tagihan, PasienModel pasien);
}
