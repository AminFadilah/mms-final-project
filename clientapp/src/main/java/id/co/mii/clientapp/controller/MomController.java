package id.co.mii.clientapp.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.model.MOM;
import id.co.mii.clientapp.model.Meeting;
import id.co.mii.clientapp.model.dto.MeetingDTO;
import id.co.mii.clientapp.service.MeetingService;
import id.co.mii.clientapp.service.MomService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("mom")
@AllArgsConstructor
public class MomController {
    private MomService momService;
    private MeetingService meetingService;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model, Authentication authentication) {
        MOM mom = momService.getById(id);
        Meeting meetings = meetingService.getById(id);
        if (mom == null) {
            boolean momVal = false;
            model.addAttribute("momVal", momVal);
        }else{
            boolean momVal = true;
            model.addAttribute("momVal", momVal);
        }

        String username = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        
        model.addAttribute("mom", mom);
        model.addAttribute("username", username);
        model.addAttribute("meeting", meetings);
        Meeting meeting = meetingService.getById(id);
        model.addAttribute("meeting", meeting);
        return "meeting/view-mom";
    }
    @GetMapping("/edit-mom/{id}")
    public String editMomView(@PathVariable Integer id, Model model) {
        MOM mom = momService.getById(id);
        Meeting meeting = meetingService.getById(id);
        model.addAttribute("mom", mom);
        model.addAttribute("meeting", meeting);

        return "meeting/edit-mom";
    }
    @PutMapping("/save/{id}")
    public String update(@PathVariable Integer id, MOM mom) {
        momService.update(id, mom);
        return "redirect:/meeting/past";
    }

}
