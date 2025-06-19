package id.co.mii.clientapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("room")
@AllArgsConstructor
// @PreAuthorize("hasRole('ADMIN')")
public class RoomController {
    @GetMapping
    public String index(Model model, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        System.out.println(authentication.getAuthorities());

        System.out.println(isAdmin);
        return "room/index";
    }
}
