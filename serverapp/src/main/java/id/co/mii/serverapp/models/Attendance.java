package id.co.mii.serverapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "participant")
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Participant participant;

    @ManyToOne
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "meeting")
    private Meeting meeting;

}
