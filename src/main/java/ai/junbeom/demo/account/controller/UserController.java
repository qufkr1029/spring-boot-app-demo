package ai.junbeom.demo.account.controller;

import ai.junbeom.demo.account.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    @GetMapping({"/login"})
    public String loginPage(HttpServletRequest request, Model model) {
        String clientIp = request.getRemoteAddr();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        model.addAttribute("clientIp", clientIp);
        model.addAttribute("requestTime", formattedNow);

        return "account/login";
    }

    @GetMapping({"/login2"})
    public String loginPage2(HttpServletRequest request, Model model) {
        String clientIp = request.getRemoteAddr();
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        model.addAttribute("clientIp", clientIp);
        model.addAttribute("requestTime", formattedNow);

        return "account/login";
    }
}
