package callumboyd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tap {
    private int id;
    private Date tapDate;
    private TapType tapType;
    private String stopId;
    private String companyId;
    private String busId;
    private String pan;

}
