package id.co.mii.serverapp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

import id.co.mii.serverapp.models.Meeting;
import id.co.mii.serverapp.models.Room;

@Component
public class MeetingSchedulerService {

    private final MeetingService meetingService;
    private final RoomService roomService;

    public MeetingSchedulerService(MeetingService meetingService, RoomService roomService) {
        this.meetingService = meetingService;
        this.roomService = roomService;
    }

    @Scheduled(fixedRate = 60000) // Set to run every minute, adjust as needed
    public void updateRoomAvailability() {
        List<Meeting> meetings = meetingService.getAll();

        for (Meeting meeting : meetings) {
            if (meeting.getEndMeeting().isBefore(LocalDateTime.now())) {
                Room room = meeting.getRoom();
                if (room != null) {
                    boolean isRoomStillUsed = meetingService.isRoomUsedInUpcomingMeetings(room, meeting);
                    System.out.println(isRoomStillUsed);
                    if (isRoomStillUsed) {
                        roomService.updateRoomAvailability(room, false);
                    
                    }else{
                        roomService.updateRoomAvailability(room, true);
                    }
                }
            }
        }
    }
}