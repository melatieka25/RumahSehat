package TA_C_SHA_90.RumahSehatWeb.controller;

import TA_C_SHA_90.RumahSehatWeb.model.*;
import TA_C_SHA_90.RumahSehatWeb.service.AppointmentService;
import TA_C_SHA_90.RumahSehatWeb.service.JumlahService;
import TA_C_SHA_90.RumahSehatWeb.service.ObatService;
import TA_C_SHA_90.RumahSehatWeb.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/resep")
public class ResepController {
    @Qualifier("resepServiceImpl")
    @Autowired
    private ResepService resepService;

    @Autowired
    private ObatService obatService;

    @Autowired
    private JumlahService jumlahService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/create-resep")
    public String addResepFormPage(@RequestParam(value = "kodeApp") String kodeApp, Model model) {
        ResepModel resep = new ResepModel();
        List<ObatModel> listObat = obatService.getListObat();
        List<JumlahModel> listJumlah = new ArrayList<>();

        resep.setListJumlah(listJumlah);
        resep.getListJumlah().add(new JumlahModel());

        model.addAttribute("kodeApp", kodeApp);
        model.addAttribute("resep", resep);
        model.addAttribute("listObatExisting", listObat);
        return "resep/form-add-resep";
    }

    @PostMapping(value = "/create-resep", params = { "addRow" })
    private String addRowObatMultiple(@ModelAttribute ResepModel resep, @RequestParam(value = "kodeApp") String kodeApp, Model model) {
        if (resep.getListJumlah() == null || resep.getListJumlah().size() == 0) {
            resep.setListJumlah(new ArrayList<>());
        }
        resep.getListJumlah().add(new JumlahModel());
        List<JumlahModel> listJumlah = jumlahService.getListJumlah();

        model.addAttribute("resep", resep);
        List<ObatModel> listObat = obatService.getListObat();
        model.addAttribute("kodeApp", kodeApp);
        model.addAttribute("listObatExisting", listObat);
        model.addAttribute("listJumlahExisting", listJumlah);

        return "resep/form-add-resep";
    }

    @PostMapping(value = "/create-resep", params = { "deleteRow" })
    private String deleteRowObatMultiple(@ModelAttribute ResepModel resep, @RequestParam("deleteRow") Integer rowId, @RequestParam(value = "kodeApp") String kodeApp, Model model) {
        resep.getListJumlah().remove(rowId.intValue());

        List<JumlahModel> listJumlah = jumlahService.getListJumlah();

        model.addAttribute("resep", resep);
        List<ObatModel> listObat = obatService.getListObat();
        model.addAttribute("kodeApp", kodeApp);
        model.addAttribute("listObatExisting", listObat);
        model.addAttribute("listJumlahExisting", listJumlah);

        return "resep/form-add-resep";
    }

    @PostMapping(value = "/create-resep", params = { "save" })
    public String addResepSubmitPage(@ModelAttribute ResepModel resep, @RequestParam(value = "kodeApp") String kodeApp, Model model, RedirectAttributes redirectAttrs) {
        List<JumlahModel> listJumlah = resep.getListJumlah();
        AppointmentModel appointment = appointmentService.getDetailAppointment(kodeApp);

        if (listJumlah == null) {
            redirectAttrs.addFlashAttribute("error", "Resep harus memiliki setidaknya 1 obat.");
            return "redirect:/resep/create-resep";
        }

        for (JumlahModel jumlah : listJumlah) {
            jumlah.setResep(resep);
            ObatModel obat = obatService.getObatById(jumlah.getObat().getId());
            jumlah.setObat(obat);
            JumlahKey jumlahKey = new JumlahKey(obat.getId(), resep.getId());
            jumlah.setId(jumlahKey);
        }

        resep.setListJumlah(listJumlah);
        resepService.addResep(resep);
        resep.setAppointment(appointment);
        appointment.setResep(resep);

        model.addAttribute("resep", resep);
        redirectAttrs.addFlashAttribute("message", "Resep dengan id " + resep.getId() + " telah berhasil ditambahkan!");

        List<ObatModel> listObat = obatService.getListObat();
        model.addAttribute("listObatExisting", listObat);
        return "redirect:/resep/detail?id=" + resep.getId();
    }

    @GetMapping("")
    public String listResep(Model model) {
        List<ResepModel> listResep = resepService.getListResep();
        model.addAttribute("listResep", listResep);
        return "resep/viewall-resep";
    }

    @GetMapping("/detail")
    public String viewDetailResepPage(@RequestParam(value = "id") Long id, Model model) {
        ResepModel resep = resepService.getResepById(id);
        List<JumlahModel> listJumlah = resep.getListJumlah();
        model.addAttribute("resep", resep);
        model.addAttribute("listJumlah", listJumlah);
        return "resep/view-resep";
    }

    @GetMapping("/confirm")
    public String confirmResep(@RequestParam(value = "id") Long id, Model model, RedirectAttributes redirectAttrs) {
        ResepModel resep = resepService.getResepById(id);

        Boolean confirmed = resepService.confirmResep(resep);

        if (confirmed) {
            redirectAttrs.addFlashAttribute("message", "Resep dengan id " + resep.getId() + " telah berhasil dikonfirmasi!");
        } else {
            redirectAttrs.addFlashAttribute("error", "Stok obat tidak cukup.");
        }
        return "redirect:/resep/detail?id=" + id;
    }

}
