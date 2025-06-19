package id.co.mii.clientapp.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
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
    private Room room;
    private Status status;
    private Participant initiator;
    private Participant noteTaker;
    private List<Participant> attendees;
    private MOM mom;
}