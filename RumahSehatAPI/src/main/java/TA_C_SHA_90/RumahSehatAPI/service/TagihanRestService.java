package TA_C_SHA_90.RumahSehatAPI.service;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;

import java.util.List;

public interface TagihanRestService {
    TagihanModel createTagihan(TagihanModel tagihan);
    List<TagihanModel> getTagihanList();
    TagihanModel getTagihanByAppointment(AppointmentModel appointment);
}
