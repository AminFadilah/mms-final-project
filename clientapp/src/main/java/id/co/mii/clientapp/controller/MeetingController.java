package id.co.mii.clientapp.controller;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.model.Attendance;
import id.co.mii.clientapp.model.MOM;
import id.co.mii.clientapp.model.Meeting;
import id.co.mii.clientapp.model.Participant;
import id.co.mii.clientapp.model.Room;
import id.co.mii.clientapp.model.dto.MeetingDTO;
import id.co.mii.clientapp.service.AttendanceService;
import id.co.mii.clientapp.service.MeetingAuthorizationService;
import id.co.mii.clientapp.service.MeetingService;
import id.co.mii.clientapp.service.MomService;
import id.co.mii.clientapp.service.ParticipantService;
import id.co.mii.clientapp.service.RoomService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("meeting")
@AllArgsConstructor
public class MeetingController {
    private MeetingService meetingService;
    private RoomService roomService;
    private ParticipantService participantService;
    private MomService momService;
    private MeetingAuthorizationService meetingAuthorizationService;
    private AttendanceService attendanceService;

    // @GetMapping
    // public String index(Model model) {
    // model.addAttribute("meetings", meetingService.getAll());
    // return "meeting/index";
    // }

    @GetMapping("/upcoming")
    public String upcoming(Model model, Authentication authentication) {
        List<Meeting> meetings = meetingService.getAllUpcoming();

        LocalDateTime currentTime = LocalDateTime.now();

        String username = authentication.getName();

        List<Meeting> sortedMeetings = meetings.stream()
                // .filter(meeting -> meeting.getStartMeeting().isAfter(currentTime) && meeting.getEndMeeting().isAfter(currentTime))
                
                .sorted(Comparator.comparing(Meeting::getStartMeeting))
                .collect(Collectors.toList());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", username);
        model.addAttribute("meetings", sortedMeetings);
        return "meeting/index";
    }

    @GetMapping("/past")
    public String past(Model model, Authentication authentication) {
        List<Meeting> meetings = meetingService.getAllPast();

        String username = authentication.getName();
        System.out.println(username);

        LocalDateTime currentTime = LocalDateTime.now();

        List<Meeting> sortedMeetings = meetings.stream()
                .filter(meeting -> meeting.getEndMeeting().isBefore(currentTime))
                .sorted(Comparator.comparing(Meeting::getEndMeeting).reversed())
                .collect(Collectors.toList());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", username);
        model.addAttribute("meetings", sortedMeetings);
        return "meeting/past-index";
    }

    @GetMapping("/deleted")
    public String allDelete(Model model, Authentication authentication) {
        List<Meeting> meetings = meetingService.getAllDelete();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("meetings", meetings);
        return "meeting/cancelled-index";
    }

    @GetMapping("/create")
    public String createView(MeetingDTO meetingDTO, Model model, Authentication authentication) {
        List<Room> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        List<Participant> participants = participantService.getAll();
        model.addAttribute("participants", participants);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        return "meeting/create-form";
    }

    @PostMapping
    public String create(MeetingDTO meetingDTO) {
        meetingService.create(meetingDTO);
        return "redirect:/meeting/upcoming";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Integer id, MeetingDTO meetingDTO, Model model,
            Authentication authentication) {
        Meeting meeting = meetingService.getById(id);
        List<Attendance> attendance = attendanceService.getAll();
        System.out.println(authentication.getName());

        boolean canUpdate = meetingAuthorizationService.canUpdateMeeting(authentication, id);
        if (!canUpdate) {
            return "redirect:/meeting/upcoming";
        }

        meetingDTO.setName(meeting.getName());
        meetingDTO.setDescription(meeting.getDescription());
        meetingDTO.setStartMeeting(meeting.getStartMeeting());
        meetingDTO.setEndMeeting(meeting.getEndMeeting());
        meetingDTO.setIsOnline(meeting.getIsOnline());
        meetingDTO.setLinkRoom(meeting.getLinkRoom());
        if (meeting.getLinkRoom() == null) {
            meetingDTO.setRoomId(meeting.getRoom().getId());
        }

        meetingDTO.setNoteTakerId(meeting.getNoteTaker().getId());
        List<Room> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        List<Participant> participants = participantService.getAll();
        model.addAttribute("participants", participants);

        List<String> participantNames = attendance.stream()
                .filter(att -> att.getMeeting() != null && att.getMeeting().getId() == id)
                .map(att -> att.getParticipant().getName())
                .collect(Collectors.toList());

        System.out.println(participantNames);

        model.addAttribute("participantNames", participantNames);

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        return "meeting/update-form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, MeetingDTO meetingDTO) {

        meetingService.update(id, meetingDTO);
        return "redirect:/meeting/upcoming";
    }

    // add mom
    @GetMapping("/add-mom/{id}")
    public String momView(@PathVariable Integer id, Model model, Authentication authentication) {
        Meeting meeting = meetingService.getById(id);
        MOM mom = new MOM();

        boolean canAdd = meetingAuthorizationService.canAddMom(authentication, id);
        if (!canAdd) {
            return "redirect:/meeting/past";
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("meeting", meeting);
        model.addAttribute("mom", mom);

        return "meeting/add-mom";
    }

    @PostMapping("/add-mom/{id}")
    public String mom(@PathVariable Integer id, MOM mom) {

        mom.setMeeting(id);
        momService.create(mom);
        return "redirect:/meeting/past";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        meetingService.delete(id);
        return "redirect:/meeting/deleted";
    }

    @PostMapping("/restore/{id}")
    public String restore(@PathVariable Integer id) {
        meetingService.restore(id);
        return "redirect:/meeting/past";
    }
}