package id.co.mii.serverapp.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startMeeting;

    @Column(nullable = false)
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endMeeting;

    @Column
    private String linkRoom;

    @Column
    private Boolean isOnline;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Participant initiator;

    @ManyToOne
    @JoinColumn(name = "note_taker_id", nullable = false)
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Participant noteTaker;

    @OneToMany(mappedBy = "meeting")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<TrackingHistory> trackingHistories;

    @OneToOne(mappedBy = "meeting", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MOM mom;

    @OneToMany(mappedBy = "meeting")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Attendance> attendances;

}
