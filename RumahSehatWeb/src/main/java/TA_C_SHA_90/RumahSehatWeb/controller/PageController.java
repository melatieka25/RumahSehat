package TA_C_SHA_90.RumahSehatWeb.controller;

import TA_C_SHA_90.RumahSehatWeb.Setting.Setting;
import TA_C_SHA_90.RumahSehatWeb.model.AdminModel;
import TA_C_SHA_90.RumahSehatWeb.model.UserModel;
import TA_C_SHA_90.RumahSehatWeb.security.xml.Attributes;
import TA_C_SHA_90.RumahSehatWeb.security.xml.ServiceResponse;
import TA_C_SHA_90.RumahSehatWeb.service.AdminService;
import TA_C_SHA_90.RumahSehatWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

import static TA_C_SHA_90.RumahSehatWeb.security.SsoWhitelist.isWhiteList;

@Controller
public class PageController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    ServerProperties serverProperties;

    private WebClient webClient = WebClient.builder().build();

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(
            @RequestParam(value = "ticket", required = false) String ticket,
            HttpServletRequest request, RedirectAttributes redirectAttrs
    ) {
        ServiceResponse serviceResponse = this.webClient.get().uri(
                String.format(
                        Setting.SERVER_VALIDATE_TICKET, ticket,
                        Setting.CLIENT_LOGIN
                )
        ).retrieve().bodyToMono(ServiceResponse.class).block();
        		
        if(serviceResponse == null)
			return new ModelAndView("error/500-logged-out");

        Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
        String username = serviceResponse.getAuthenticationSuccess().getUser();
        boolean canLogin = false;

        for (String admin: isWhiteList) {
            if (admin.equals(username)) {
                canLogin = true;
            }
        }
        if (canLogin == false) {
            redirectAttrs.addFlashAttribute("notLogin", "Anda belum terdaftar sebagai admin");
            return new ModelAndView("redirect:/login");
        }

		AdminModel admin;
		
		if (userService.getUserByUsername(username) == null){
            admin = new AdminModel();
            admin.setEmail(username + "@ui.ac.id");
            admin.setNama(attributes.getNama());
            admin.setPassword("tkapap");
            admin.setUsername(username);
            admin.setIsSso(true);
            admin.setRole("Admin");
            adminService.addAdmin(admin);
        }
		
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "tkapap");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession httpSession = request.getSession(true) ;
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,securityContext);

        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/login-sso")
    public ModelAndView loginSSO(){
        return new ModelAndView("redirect:"+ Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

    @GetMapping (value = "/logout-sso")
    public ModelAndView logoutSSO(Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());
        System.out.println(user.getUsername());
        if (user.getIsSso() == false){
            return new ModelAndView("redirect:/logout");
        }
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }

}
