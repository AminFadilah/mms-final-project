package id.co.mii.clientapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.model.dto.LoginRequest;
import id.co.mii.clientapp.service.AuthService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("login")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @GetMapping
    public String loginView(LoginRequest loginRequest) {
        return "auth/login";
    }

    @PostMapping
    public String login(LoginRequest loginRequest) {
        if (!authService.login(loginRequest)) {
            return "redirect:/login?error=true";
        }
        System.out.println("Login success");
        return "redirect:/meeting/upcoming";
    }
}
