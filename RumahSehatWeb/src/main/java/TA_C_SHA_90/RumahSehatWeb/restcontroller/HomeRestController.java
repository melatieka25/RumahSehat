package TA_C_SHA_90.RumahSehatWeb.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {
    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Selamat datang di RumahSehat";
    }
}
