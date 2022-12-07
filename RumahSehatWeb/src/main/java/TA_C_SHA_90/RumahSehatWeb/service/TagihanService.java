package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatWeb.model.TagihanModel;

import java.util.List;

public interface TagihanService {
    TagihanModel createTagihan(TagihanModel tagihan);
    List<TagihanModel> getTagihanList();
    TagihanModel getTagihanByAppointment(AppointmentModel appointment);
	
	List<List<Integer>> getMonthlyStats(Integer year);
	List<List<Integer>> getDailyStats(Integer year, Integer month);
	List<Integer> getAgeRangeStats(Integer[] ageRange);
	List<Integer> getPaidAgeRangeStats(Integer[] ageRange);
}
