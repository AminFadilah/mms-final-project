package id.co.mii.clientapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Boolean isInternal;
    private User user;
}
