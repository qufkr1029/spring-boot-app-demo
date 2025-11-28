package ai.junbeom.demo.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private static final org.apache.logging.log4j.Logger log4j2Logger = LogManager.getLogger(AccountController.class);

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        // Get client IP address
        String clientIp = request.getRemoteAddr();

        // Get current time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        // Add attributes to the model
        model.addAttribute("clientIp", clientIp);
        model.addAttribute("requestTime", formattedNow);
        
        // Logging with SLF4J
        logger.info("로그백으로 안녕하세요");

        // Logging directly with Log4j2 API
        log4j2Logger.info("Log4j2로 안녕하세요");

        return "login";
    }
}
