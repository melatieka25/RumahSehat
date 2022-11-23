package TA_C_SHA_90.RumahSehatWeb.controller;

import TA_C_SHA_90.RumahSehatWeb.PasswordManager;
import TA_C_SHA_90.RumahSehatWeb.model.DokterModel;
import TA_C_SHA_90.RumahSehatWeb.model.UserModel;
import TA_C_SHA_90.RumahSehatWeb.service.DokterService;
import TA_C_SHA_90.RumahSehatWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dokter")
public class DokterController {
    @Qualifier("dokterServiceImpl")
    @Autowired
    private DokterService dokterService;

    @Autowired
    private UserService userService;

	@Autowired
    private RoleService roleService;

    @GetMapping("/create-dokter")
    public String addDokterFormPage(Model model) {
        model.addAttribute("dokter", new DokterModel());
        return "dokter/form-add-dokter";
    }

    @PostMapping("/create-dokter")
    public String addDokterSubmitPage(@ModelAttribute DokterModel dokter, Model model, RedirectAttributes redirectAttrs) {
        dokter.setRole(roleService.getByName("Dokter"));
        UserModel sameUsername = userService.getUserByUsername(dokter.getUsername());
        UserModel sameEmail = userService.getUserByEmail(dokter.getEmail());

        //Membuat objek MahasiswaModel
        if (sameUsername == null && sameEmail == null){
            if (PasswordManager.validationChecker(dokter.getPassword())){
                dokter.setIsSso(false);
                dokterService.addDokter(dokter);
                redirectAttrs.addFlashAttribute("message", "Dokter dengan username " + dokter.getUsername() + " telah berhasil ditambahkan!");
                return "redirect:/dokter";
            } else {
                redirectAttrs.addFlashAttribute("error", "Password tidak mengandung huruf besar/huruf kecil/angka/simbol atau kurang dari 8 karakter.");
                return "redirect:/dokter/create-dokter";
            }
        } else if (sameUsername != null) {
            redirectAttrs.addFlashAttribute("error", "User dengan username " + dokter.getUsername() + " sudah pernah ditambahkan sebelumnya. Coba lagi dengan username lain!");
            return "redirect:/dokter/create-dokter";
        } else {
            redirectAttrs.addFlashAttribute("error", "User dengan email " + dokter.getEmail() + " sudah pernah ditambahkan sebelumnya. Coba lagi dengan email lain!");
            return "redirect:/dokter/create-dokter";
        }

    }

    @GetMapping("")
    public String listDokter(Model model) {
        List<DokterModel> listDokter = dokterService.getListDokter();
        model.addAttribute("listDokter", listDokter);
        return "dokter/list-dokter";
    }
}
