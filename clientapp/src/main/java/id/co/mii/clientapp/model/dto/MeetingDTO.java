package id.co.mii.clientapp.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import id.co.mii.clientapp.model.MOM;
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
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startMeeting;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endMeeting;
    private String linkRoom;
    private Boolean isOnline;
    private Integer roomId;
    private Integer initiatorId;
    private Integer noteTakerId;
    List<Integer> attendees;
    private MOM mom;
}
