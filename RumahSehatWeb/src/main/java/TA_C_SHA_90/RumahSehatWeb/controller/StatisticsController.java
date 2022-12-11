package TA_C_SHA_90.RumahSehatWeb.controller;

import TA_C_SHA_90.RumahSehatWeb.model.TagihanModel;
import TA_C_SHA_90.RumahSehatWeb.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;
import java.time.YearMonth;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private TagihanService tagihanService;
    
    @GetMapping
    public String getStatistics(@RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "month", required = false) Integer month, @RequestParam(value = "data-source", required = false) Integer dataSource, @RequestParam(value = "age-range", required = false) Integer[] ageRange, Model model) {
        String chartType = "daily";
        String[] labels;
        
        Integer[] years = new Integer[LocalDate.now().getYear() - 1970 + 1];
        
        for(int i = LocalDate.now().getYear(); i >= 1970; i--)
            years[2022 - i] = i;
        
        model.addAttribute("years", years);
        
        if(dataSource != null) {
            if(ageRange == null) {
                ageRange = new Integer[]{0, 1, 2, 3, 4};
            }
            
            // Sort and remove duplicates from age range
            ageRange = Arrays.stream(ageRange).distinct().toArray(Integer[]::new);
            Arrays.sort(ageRange);
            
            List<Integer> data;
            String dataLabel;
            
            if(dataSource == 0) {
                dataLabel = "Jumlah Tagihan Yang Dibuat";
                data = tagihanService.getAgeRangeStats(ageRange);
            } else {
                dataLabel = "Jumlah Tagihan Yang Terbayar";
                data = tagihanService.getPaidAgeRangeStats(ageRange);
            }
            
            labels = new String[ageRange.length];
            int labelsIndex = 0;
            
            String[] ageRangeLabels = {"0-15", "16-30", "31-45", "46-60", ">= 61"};
            
            for(int singularRange : ageRange)
                labels[labelsIndex++] = ageRangeLabels[singularRange];
            
            model.addAttribute("labels", labels);
            model.addAttribute("data", data);
            model.addAttribute("dataSource", dataSource);
            model.addAttribute("dataLabel", dataLabel);
            model.addAttribute("ageRangeSelected", ageRange);
            model.addAttribute("chartType", "bar");
            
            return "stats/index";
        }
        
        List<List<Integer>> data;

        if(year == null) {
            LocalDate currentDate = LocalDate.now();
            year = currentDate.getYear();
            month = currentDate.getMonthValue();
            
            data = tagihanService.getDailyStats(year, month);
        } else if(month == null) {
            data = tagihanService.getMonthlyStats(year);
            month = -1;
            chartType = "monthly";
        } else
            data = tagihanService.getDailyStats(year, month);
        
        if(chartType.equals("daily")) {
            Integer daysCount = YearMonth.of(year, month).lengthOfMonth();
            String[] days = new String[daysCount];
            for(int i = 0; i < daysCount; i++)
                days[i] = String.valueOf(i + 1);
            labels = days;
        } else {
            labels = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        }
        
        model.addAttribute("ageRangeSelected", new Integer[]{0, 1, 2, 3, 4});
        model.addAttribute("currentYear", year);
        model.addAttribute("month", month);
        model.addAttribute("labels", labels);
        model.addAttribute("data", data);
        model.addAttribute("dataSource", 0);
        model.addAttribute("chartType", "line");
        
        return "stats/index";
    }
}
