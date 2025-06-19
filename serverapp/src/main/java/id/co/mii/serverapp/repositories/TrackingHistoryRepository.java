package id.co.mii.serverapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.co.mii.serverapp.models.Meeting;
import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.models.TrackingHistory;

@Repository
public interface TrackingHistoryRepository extends JpaRepository<TrackingHistory, Integer> {

    List<TrackingHistory> findByMeetingId(Integer meetingId);
    List<TrackingHistory> findByMeetingAndStatus(Meeting meeting, Status status);
    
}
