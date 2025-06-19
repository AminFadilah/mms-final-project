package id.co.mii.clientapp.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.clientapp.model.Participant;
import id.co.mii.clientapp.model.Room;
import id.co.mii.clientapp.service.AttendanceService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/attendance/attendee")
@AllArgsConstructor
public class RestAttendanceController {
   private AttendanceService attendanceService;
@GetMapping("/{meetingId}")
    public List<Participant> getAttendeeBymeeting(@PathVariable Integer meetingId){
        return attendanceService.getAttendeeByMeeting(meetingId);
    }
}
