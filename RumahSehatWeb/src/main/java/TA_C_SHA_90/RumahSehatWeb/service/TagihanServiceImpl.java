package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatWeb.model.PasienModel;
import TA_C_SHA_90.RumahSehatWeb.model.TagihanModel;
import TA_C_SHA_90.RumahSehatWeb.repository.AppointmentDb;
import TA_C_SHA_90.RumahSehatWeb.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Arrays;
import java.time.YearMonth;

@Service
@Transactional
public class TagihanServiceImpl implements TagihanService{
    @Autowired
    private TagihanDb tagihanDb;

    @Override
    public TagihanModel createTagihan(TagihanModel tagihan) {
        return tagihanDb.save(tagihan);
    }

    @Override
    public List<TagihanModel> getTagihanList() {
        return tagihanDb.findAll();
    }

    @Override
    public TagihanModel getTagihanByAppointment(AppointmentModel appointment) {
        Optional<TagihanModel> tagihan = tagihanDb.findByAppointment(appointment);
        if (tagihan.isPresent()) {
            return tagihan.get();
        } else {
            throw new NoSuchElementException();
        }
    }
	
	@Override
	public List<List<Integer>> getMonthlyStats(Integer year) {
		List<TagihanModel> tagihanList = tagihanDb.findByYear(year);
		List<List<Integer>> monthlyStats = new ArrayList<>();
		
		for(int k = 0; k < 5; k++) {
			monthlyStats.add(new ArrayList<>());
			for(int i = 0; i < 12; i++)
				monthlyStats.get(k).add(0);
		}
		
		for(TagihanModel tagihan : tagihanList) {
			Integer tagihanMonth = tagihan.getTanggalTerbuat().getMonthValue() - 1;
			Integer umur = tagihan.getAppointment().getPasien().getUmur();
			
			List<Integer> ageCohortList;
			
			if(umur <= 15)
				ageCohortList = monthlyStats.get(0);
			else if(umur > 15 && umur <= 30)
				ageCohortList = monthlyStats.get(1);
			else if(umur > 30 && umur <= 45)
				ageCohortList = monthlyStats.get(2);
			else if(umur > 45 && umur <= 60)
				ageCohortList = monthlyStats.get(3);
			else
				ageCohortList = monthlyStats.get(4);
			
			ageCohortList.set(tagihanMonth, ageCohortList.get(tagihanMonth) + 1);
		}
		
		return monthlyStats;
	}
	
	@Override
	public List<List<Integer>> getDailyStats(Integer year, Integer month) {
		List<TagihanModel> tagihanList = tagihanDb.findByYearMonth(year, month);
		List<List<Integer>> dailyStats = new ArrayList<>();
		
		for(int k = 0; k < 5; k++) {
			dailyStats.add(new ArrayList<>());
			for(int i = 0; i < YearMonth.of(year, month).lengthOfMonth(); i++)
				dailyStats.get(k).add(0);
		}
		
		for(TagihanModel tagihan : tagihanList) {
			Integer tagihanDay = tagihan.getTanggalTerbuat().getDayOfMonth() - 1;
			Integer umur = tagihan.getAppointment().getPasien().getUmur();
			
			System.out.println("UMUR: " + umur);
			
			List<Integer> ageCohortList;
			
			if(umur <= 15)
				ageCohortList = dailyStats.get(0);
			else if(umur > 15 && umur <= 30)
				ageCohortList = dailyStats.get(1);
			else if(umur > 30 && umur <= 45)
				ageCohortList = dailyStats.get(2);
			else if(umur > 45 && umur <= 60)
				ageCohortList = dailyStats.get(3);
			else
				ageCohortList = dailyStats.get(4);
			
			ageCohortList.set(tagihanDay, ageCohortList.get(tagihanDay) + 1);
		}
		
		return dailyStats;
	}
	
	private List<Integer> getTagihanBarStats(Integer[] ageRange, List<TagihanModel> tagihanList) {
		List<Integer> tagihanBarStats = new ArrayList<>();
		
		boolean has0_15 = false, has16_30 = false, has31_45 = false, has46_60 = false, has61 = false;
		int index0_15 = -1, index16_30 = -1, index31_45 = -1, index46_60 = -1, index61 = -1;
		int counter = 0;
		
		// Initialize flags for selected age ranges
		for(int age : ageRange) {
			if(age == 0 && !has0_15) {
				has0_15 = true;
				index0_15 = counter++;
			} else if(age == 1 && !has16_30) {
				has16_30 = true;
				index16_30 = counter++;
			} else if(age == 2 && !has31_45) {
				has31_45 = true;
				index31_45 = counter++;
			} else if(age == 3 && !has46_60) {
				has46_60 = true;
				index46_60 = counter++;
			} else if(age == 4 && !has61) {
				has61 = true;
				index61 = counter++;
			}
		}
		
		// Initialize age range stats
		for(int k = 0; k < ageRange.length; k++) {
			tagihanBarStats.add(0);
		}
		
		for(TagihanModel tagihan : tagihanList) {
			Integer umur = tagihan.getAppointment().getPasien().getUmur();
			
			if(umur <= 15 && has0_15)
				tagihanBarStats.set(index0_15, tagihanBarStats.get(index0_15) + 1);
			else if(umur > 15 && umur <= 30 && has16_30)
				tagihanBarStats.set(index16_30, tagihanBarStats.get(index16_30) + 1);
			else if(umur > 30 && umur <= 45 && has31_45)
				tagihanBarStats.set(index31_45, tagihanBarStats.get(index31_45) + 1);
			else if(umur > 45 && umur <= 60 && has46_60)
				tagihanBarStats.set(index46_60, tagihanBarStats.get(index46_60) + 1);
			else if(umur > 60 && has61)
				tagihanBarStats.set(index61, tagihanBarStats.get(index61) + 1);
		}
		
		return tagihanBarStats;
	}
	
	@Override
	public List<Integer> getAgeRangeStats(Integer[] ageRange) {
		return getTagihanBarStats(ageRange, tagihanDb.findAll());
	}
	
	@Override
	public List<Integer> getPaidAgeRangeStats(Integer[] ageRange) {
		return getTagihanBarStats(ageRange, tagihanDb.findAllPaid());
	}
}
