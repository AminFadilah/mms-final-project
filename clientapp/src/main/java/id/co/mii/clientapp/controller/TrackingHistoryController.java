package id.co.mii.clientapp.controller;

import id.co.mii.clientapp.model.Meeting;
import id.co.mii.clientapp.model.TrackingHistory;
import id.co.mii.clientapp.service.MeetingService;
import id.co.mii.clientapp.service.TrackingHistoryService;
import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tracking-history")
public class TrackingHistoryController {

    private final TrackingHistoryService trackingHistoryService;
    private final MeetingService meetingService;

    @GetMapping("/{id}")
    public String getTrackingHistoryByMeetingId(@PathVariable Integer id, Model model, Authentication authentication) {
        // Mendapatkan data tracking history berdasarkan ID meeting
        List<TrackingHistory> trackingHistoryList = trackingHistoryService.getByMeetingId(id);
        model.addAttribute("trackingHistoryList", trackingHistoryList);

        // Mendapatkan informasi meeting yang dilacak
        Meeting meeting = meetingService.getById(id);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // System.out.println(authentication.getAuthorities());
        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("meeting", meeting);

        return "meeting/tracking-history";
    }
}
