package id.co.mii.clientapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MOM {
    private Integer id;
    private String discussion;
    private String result;
    private Integer meeting;
}
