package id.co.mii.serverapp.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Attendance;
import id.co.mii.serverapp.models.MOM;
import id.co.mii.serverapp.models.Meeting;
import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.models.TrackingHistory;
import id.co.mii.serverapp.models.dto.request.MeetingDTO;
import id.co.mii.serverapp.models.dto.request.MomDTO;
import id.co.mii.serverapp.repositories.MOMRepository;
import id.co.mii.serverapp.repositories.MeetingRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MOMService {
    private MOMRepository momRepository;
    private MeetingRepository meetingRepository;
    private StatusService statusService;
    private TrackingHistoryService trackingHistoryService;
    private MeetingService meetingService;

    public List<MOM> getAll() {
        return momRepository.findAll();
    }

    public MOM getById(Integer id) {
        
        return momRepository.findById(id).orElse(null);
    }
    


    public MOM create(MomDTO momDTO) {
        Integer meetingId = momDTO.getMeeting();
        MOM mom = new MOM();
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null) ;
        if ( meeting != null) {
            momDTO.setMeeting(meetingId);
            mom.setDiscussion(momDTO.getDiscussion());
            mom.setResult(momDTO.getResult());
            meeting.setId(momDTO.getMeeting());
            mom.setMeeting(meeting);
        

            TrackingHistory th = new TrackingHistory();
            th.setDate(LocalDateTime.now());
            th.setMeeting(meeting);
            th.setStatus(statusService.getById(5));
            th.setParticipant(meeting.getNoteTaker());
            trackingHistoryService.create(th);
            return momRepository.save(mom);
        }
        return null;
    }

    public MOM update(Integer id, MOM mom) {
        Meeting meeting = meetingRepository.findById(id).orElse(null) ;
        getById(id);
        mom.setId(id);
        TrackingHistory th = new TrackingHistory();
        th.setDate(LocalDateTime.now());
        th.setMeeting(meeting);
        th.setStatus(statusService.getById(6));
        th.setParticipant(meeting.getNoteTaker());
        trackingHistoryService.create(th);
        return momRepository.save(mom);
    }

    public MOM delete(Integer id) {
        MOM mom = getById(id);
        momRepository.delete(mom);
        return mom;
    }
}
