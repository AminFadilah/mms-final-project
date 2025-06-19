package id.co.mii.serverapp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Meeting;
import id.co.mii.serverapp.models.Participant;
import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.models.TrackingHistory;
import id.co.mii.serverapp.repositories.TrackingHistoryRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TrackingHistoryService {
    

    private TrackingHistoryRepository trackingHistoryRepository;

    public List<TrackingHistory> getAll() {
        return trackingHistoryRepository.findAll();
    }

    public TrackingHistory getById(Integer id) {
        return trackingHistoryRepository.findById(id).orElse(null);
    }

    public TrackingHistory create(TrackingHistory trackingHistory) {
        return trackingHistoryRepository.save(trackingHistory);
    }

    public TrackingHistory update(Integer id, TrackingHistory trackingHistory) {
        getById(id);
        trackingHistory.setId(id);
        return trackingHistoryRepository.save(trackingHistory);
    }

    public TrackingHistory delete(Integer id) {
        TrackingHistory trackingHistory = getById(id);
        trackingHistoryRepository.delete(trackingHistory);
        return trackingHistory;
    }
    
    public List<TrackingHistory> getByMeetingId(Integer meetingId) {
        return trackingHistoryRepository.findByMeetingId(meetingId);
    }

    public List<TrackingHistory> findByMeetingAndStatus(Meeting meeting, Status status) {
        return trackingHistoryRepository.findByMeetingAndStatus(meeting, status);
}
}
