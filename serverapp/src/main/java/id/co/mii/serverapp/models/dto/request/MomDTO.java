package id.co.mii.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomDTO {
    private Integer id;
    private String discussion;
    private String result;
    private String followUp;
    private Integer meeting;
}
