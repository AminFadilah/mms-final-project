package id.co.mii.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailRequest {
    private String to;
    private String subject;
    private String name;
    private String text;
    private String myname;
    private String attach;

}