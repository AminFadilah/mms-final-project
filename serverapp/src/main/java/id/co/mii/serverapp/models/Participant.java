package id.co.mii.serverapp.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_participant")
@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String phone;

    @Column
    private Boolean isInternal;

    @OneToOne(mappedBy = "participant", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @OneToMany(mappedBy = "initiator")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Meeting> meetingsAsInitiator;

    @OneToMany(mappedBy = "noteTaker")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Meeting> meetingsAsNoteTaker;

    @OneToMany(mappedBy = "participant")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<TrackingHistory> trackingHistories;
    
    @OneToMany(mappedBy = "participant")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Attendance> attendances;
}
