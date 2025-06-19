package id.co.mii.serverapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.co.mii.serverapp.models.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
 List<Attendance> findByMeetingId(Integer meetingId);
}
