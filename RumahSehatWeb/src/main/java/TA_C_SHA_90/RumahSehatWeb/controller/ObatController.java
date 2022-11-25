package TA_C_SHA_90.RumahSehatWeb.controller;

import TA_C_SHA_90.RumahSehatWeb.model.ObatModel;
import TA_C_SHA_90.RumahSehatWeb.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/obat")
public class ObatController {
    @Autowired
    private ObatService obatService;
	
	@GetMapping
    public String listObat(Model model) {
        List<ObatModel> listObat = obatService.getListObat();
        model.addAttribute("listObat", listObat);
        return "obat/list-obat";
    }
	
	@GetMapping("stok")
    public String formStokObat(@RequestParam(value = "id") String id, Model model) {
        ObatModel obat = obatService.getObatById(id);
        model.addAttribute("obat", obat);
        return "obat/form-update-stok-obat";
    }
	
	@PostMapping("stok")
    public String updateStokObat(@RequestParam("id") String id, @RequestParam("stok") String stok, Model model) {
        ObatModel obat = obatService.getObatById(id);
		obat.setStok(Integer.valueOf(stok));
		obatService.updateObat(obat);
        return "redirect:/obat";
    }
}
