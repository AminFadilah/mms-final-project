package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Attendance;
import id.co.mii.serverapp.models.Participant;
import id.co.mii.serverapp.models.TrackingHistory;
import id.co.mii.serverapp.repositories.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AttendanceService {

    private AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Integer id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Integer id, Attendance updatedAttendance) {
        Attendance existingAttendance = getAttendanceById(id);
        if (existingAttendance != null) {
            // Set data baru ke attendance yang sudah ada
            existingAttendance.setParticipant(updatedAttendance.getParticipant());
            existingAttendance.setMeeting(updatedAttendance.getMeeting());
            return attendanceRepository.save(existingAttendance);
        }
        return null;
    }

    public void deleteAttendance(Integer id) {
        attendanceRepository.deleteById(id);
    }
    public List<Attendance> getByMeetingId(Integer meetingId) {
        return attendanceRepository.findByMeetingId(meetingId);
    }
}

