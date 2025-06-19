package id.co.mii.serverapp.models.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import id.co.mii.serverapp.models.MOM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDTO {

    private Integer id;
    private String name;
    private String description;
    private LocalDateTime startMeeting;
    private LocalDateTime endMeeting;
    private String linkRoom;
    private Boolean isOnline;
    private Integer roomId;
    private Integer statusId;
    private Integer initiatorId;
    private Integer noteTakerId;
    private List<Integer> attendees;
    private MOM mom;
}