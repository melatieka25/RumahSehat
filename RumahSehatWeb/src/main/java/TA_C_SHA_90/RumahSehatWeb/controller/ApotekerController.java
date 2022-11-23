package TA_C_SHA_90.RumahSehatWeb.controller;

import TA_C_SHA_90.RumahSehatWeb.PasswordManager;
import TA_C_SHA_90.RumahSehatWeb.model.ApotekerModel;
import TA_C_SHA_90.RumahSehatWeb.model.UserModel;
import TA_C_SHA_90.RumahSehatWeb.service.ApotekerService;
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
@RequestMapping("/apoteker")
public class ApotekerController {
    @Qualifier("apotekerServiceImpl")
    @Autowired
    private ApotekerService apotekerService;

    @Autowired
    private UserService userService;

    @GetMapping("/create-apoteker")
    public String addApotekerFormPage(Model model) {
        model.addAttribute("apoteker", new ApotekerModel());
        return "apoteker/form-add-apoteker";
    }

    @PostMapping("/create-apoteker")
    public String addApotekerSubmitPage(@ModelAttribute ApotekerModel apoteker, Model model, RedirectAttributes redirectAttrs) {
        apoteker.setIsSso(false);
        apoteker.setRole("Apoteker");
        UserModel sameUsername = userService.getUserByUsername(apoteker.getUsername());
        UserModel sameEmail = userService.getUserByEmail(apoteker.getEmail());

        //Membuat objek MahasiswaModel
        if (sameUsername == null && sameEmail == null){
            if (PasswordManager.validationChecker(apoteker.getPassword())){
                apotekerService.addApoteker(apoteker);
                redirectAttrs.addFlashAttribute("message", "Apoteker dengan username " + apoteker.getUsername() + " telah berhasil ditambahkan!");
                return "redirect:/apoteker";
            } else {
                redirectAttrs.addFlashAttribute("error", "Password tidak mengandung huruf besar/huruf kecil/angka/simbol atau kurang dari 8 karakter.");
                return "redirect:/apoteker/create-apoteker";
            }
        } else if (sameUsername != null) {
            redirectAttrs.addFlashAttribute("error", "User dengan username " + apoteker.getUsername() + " sudah pernah ditambahkan sebelumnya. Coba lagi dengan username lain!");
            return "redirect:/apoteker/create-apoteker";
        } else {
            redirectAttrs.addFlashAttribute("error", "User dengan email " + apoteker.getEmail() + " sudah pernah ditambahkan sebelumnya. Coba lagi dengan email lain!");
            return "redirect:/apoteker/create-apoteker";
        }

    }

    @GetMapping("")
    public String listApoteker(Model model) {
        List<ApotekerModel> listApoteker = apotekerService.getListApoteker();
        model.addAttribute("listApoteker", listApoteker);
        return "apoteker/list-apoteker";
    }


}
