package ai.junbeom.demo.account.controller;

import ai.junbeom.demo.account.domain.User;
import ai.junbeom.demo.account.dto.Advice;
import ai.junbeom.demo.account.dto.UserDto;
import ai.junbeom.demo.account.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RestTemplate restTemplate;

    @GetMapping({"/my-page"})
    public String myPageView(Model model, Authentication authentication) {
        String userId = authentication.getName();
        User user = userService.findUserByUserId(userId);

        model.addAttribute("userId", user.getUserId());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());

        return "account/myPage";
    }

    @GetMapping({"/login"})
    public String loginView(HttpServletRequest request, Model model, Authentication authentication) {
        // 이미 인증된 사용자는 my-page로 리다이렉트
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/my-page";
        }

        try {
            final String ADVICE_API_URL = "https://korean-advice-open-api.vercel.app/api/advice";
            Advice advice = restTemplate.getForObject(ADVICE_API_URL, Advice.class);
            model.addAttribute("advice", advice);
        } catch (Exception e) {
            log.error("Failed to fetch advice from external API", e);
            model.addAttribute("advice", new Advice("가끔은 API도 쉬고 싶을 때가 있죠.", "개발자"));
        }

        String clientIp = request.getRemoteAddr();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        model.addAttribute("clientIp", clientIp);
        model.addAttribute("requestTime", formattedNow);

        return "account/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signupView(Model model) {
        if (!model.containsAttribute("userDto")) {
            model.addAttribute("userDto", new UserDto());
        }

        return "account/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 입력 값 검증
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDto", bindingResult);
            redirectAttributes.addFlashAttribute("userDto", userDto);
            return "redirect:/signup";
        }

        try {
            userService.signup(userDto);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("userDto", userDto);
            return "redirect:/signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/check-id/{userId}")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable String userId) {
        return ResponseEntity.ok(userService.checkUserIdExists(userId));
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkEmailExists(email));
    }

}
