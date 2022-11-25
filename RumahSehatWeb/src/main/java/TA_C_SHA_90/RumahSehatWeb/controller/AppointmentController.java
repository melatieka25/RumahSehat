package TA_C_SHA_90.RumahSehatWeb.controller;

import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatWeb.model.DokterModel;
import TA_C_SHA_90.RumahSehatWeb.model.TagihanModel;
import TA_C_SHA_90.RumahSehatWeb.repository.AppointmentDb;
import TA_C_SHA_90.RumahSehatWeb.repository.DokterDb;
import TA_C_SHA_90.RumahSehatWeb.repository.TagihanDb;
import TA_C_SHA_90.RumahSehatWeb.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Qualifier("appointmentServiceImpl")
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DokterDb dokterDb;

    @Autowired
    private AppointmentDb appointmentDb;

    @Autowired
    private TagihanDb tagihanDb;

    @GetMapping("")
    public String viewAllAppointment(Model model) {
        List<AppointmentModel> listAppointment = appointmentService.getListAppointment();
        List<String> listDateTime = new ArrayList<>();
        for (int i = 0; i < listAppointment.size(); i++) {
            LocalDateTime date = listAppointment.get(i).getWaktuAwal();
            String timePattern = "dd-MM-yyyy HH:mm";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
            String dateTime = date.format(dateTimeFormatter);
            listDateTime.add(dateTime);
        }
        model.addAttribute("listAppointment", listAppointment);
        model.addAttribute("listDateTime", listDateTime);
        return "appointment/list-appointment";
    }
    @GetMapping("/detail/{kode}")
    public String detailAppointment(@PathVariable String kode, Model model, RedirectAttributes redirectAttrs) {
        AppointmentModel appointment = appointmentService.getDetailAppointment(kode);
        String timePattern = "dd-MM-yyyy HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        String dateTime = appointment.getWaktuAwal().format(dateTimeFormatter);
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateTime", dateTime);
        return "appointment/detail-appointment";
    }

    @GetMapping("/detail/{kode}/selesai")
    public String selesaikanAppointment(@PathVariable String kode, Model model, RedirectAttributes redirectAttrs) {
        AppointmentModel appointment = appointmentService.getDetailAppointment(kode);
        String timePattern = "dd-MM-yyyy HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        String dateTime = appointment.getWaktuAwal().format(dateTimeFormatter);
        LocalDateTime time = LocalDateTime.now();
        TagihanModel tagihan = new TagihanModel(null,time, null, false, appointment.getDokter().getTarif(),appointment);

        if (appointment.getResep() == null) {
            appointment.setIsDone(true);
            tagihanDb.save(tagihan);
            appointment.setTagihan(tagihan);
            appointmentDb.save(appointment);
            redirectAttrs.addFlashAttribute("message", "Status Appointment berhasil diubah!");
            model.addAttribute("appointment", appointment);
            model.addAttribute("dateTime", dateTime);
            return "redirect:/appointment/detail/" + kode;
        } else {
            if (appointment.getResep().getIsDone() == false) {
                redirectAttrs.addFlashAttribute("error", "Resep belum selesai!");
                model.addAttribute("appointment", appointment);
                model.addAttribute("dateTime", dateTime);
                return "redirect:/appointment/detail/" + kode;
            } else if (appointment.getResep().getApoteker() == null) {
                redirectAttrs.addFlashAttribute("error", "Resep belum dikonfirmasi apoteker!");
                model.addAttribute("appointment", appointment);
                model.addAttribute("dateTime", dateTime);
                return "redirect:/appointment/detail/" + kode;
            } else {
                appointment.setIsDone(true);
                tagihanDb.save(tagihan);
                appointment.setTagihan(tagihan);
                appointmentDb.save(appointment);
                redirectAttrs.addFlashAttribute("message", "Status Appointment berhasil diubah!");
                model.addAttribute("appointment", appointment);
                model.addAttribute("dateTime", dateTime);
                return "redirect:/appointment/detail/" + kode;
            }
        }
    }

    @GetMapping("/add")
    public String addAppointmentFormPage(Model model) {
        AppointmentModel appointment = new AppointmentModel();
        List<DokterModel> listDokter = dokterDb.findAll();

        model.addAttribute("appointment", appointment);
        model.addAttribute("listDokter", listDokter);
        return "form-add-appointment";
    }

    @PostMapping("/add")
    public String addAppointmentSubmitPage(@ModelAttribute AppointmentModel appointment, Model model) {
        appointment.setIsDone(false);
        return "add-appointment";
    }
}
