package id.co.mii.serverapp.controllers;

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

import id.co.mii.serverapp.models.TrackingHistory;
import id.co.mii.serverapp.services.TrackingHistoryService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/history")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class TrackingHistoryController {

    private TrackingHistoryService trackingHistoryService;

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping
    public List<TrackingHistory> getAll() {
        return trackingHistoryService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping("/{id}")
    public TrackingHistory getById(@PathVariable Integer id) {
        return trackingHistoryService.getById(id);
    }
    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN', 'CREATE_USER')")
    @PostMapping
    public TrackingHistory create(@RequestBody TrackingHistory trackingHistory) {
        return trackingHistoryService.create(trackingHistory);
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN', 'UPDATE_USER')")
    @PutMapping("/{id}")
    public TrackingHistory update(@PathVariable Integer id, @RequestBody TrackingHistory trackingHistory) {
        return trackingHistoryService.update(id, trackingHistory);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADMIN', 'DELETE_USER')")
    @DeleteMapping("/{id}")
    public TrackingHistory delete(@PathVariable Integer id) {
        return trackingHistoryService.delete(id);

    }

    @GetMapping("/meeting/{meetingId}")
    public List<TrackingHistory> getByMeetingId(@PathVariable Integer meetingId) {
        return trackingHistoryService.getByMeetingId(meetingId);
    }
}
