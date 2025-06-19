package id.co.mii.serverapp.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverapp.models.Meeting;
import id.co.mii.serverapp.models.dto.request.MeetingDTO;
import id.co.mii.serverapp.services.MeetingService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/meeting")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class MeetingController {
    private MeetingService meetingService;

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping
    public List<Meeting> getAll() {
        return meetingService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping("/upcoming")
    public List<Meeting> getAllUpcoming(Principal principal) {
        return meetingService.getAllUpcoming(principal);
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping("/past")
    public List<Meeting> getAllPast(Principal principal) {
        return meetingService.getAllPast(principal);
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping("/cancelled")
    public List<Meeting> getCancelledMeetings(Principal principal) {
        return meetingService.getAllDelete(principal);
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping("/{id}")
    public Meeting getById(@PathVariable Integer id) {
        return meetingService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN', 'CREATE_USER')")
    @PostMapping
    public Meeting create(@RequestBody MeetingDTO meetingDTO, Principal principal) {
        return meetingService.create(meetingDTO, principal);
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN', 'UPDATE_USER')")
    @PutMapping("/{id}")
    public Meeting update(@PathVariable Integer id, @RequestBody MeetingDTO meetingDTO, Principal principal) {
        return meetingService.update(id, meetingDTO, principal);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADMIN', 'DELETE_USER')")
    @DeleteMapping("/{id}")
    public Meeting delete(@PathVariable Integer id, Principal principal) {
        return meetingService.delete(id, principal);

    }
    @PreAuthorize("hasAnyAuthority('DELETE_ADMIN', 'DELETE_USER')")
    @PostMapping("/restore/{id}")
    public Meeting restoreDeletedMeeting(@PathVariable Integer id, Principal principal) {
        return meetingService.restoreDeletedMeeting(id, principal);
    }
}
