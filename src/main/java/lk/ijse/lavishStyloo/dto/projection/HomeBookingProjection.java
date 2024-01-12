package lk.ijse.lavishStyloo.dto.projection;

import lk.ijse.lavishStyloo.dto.tm.HomeBookingTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 12/2/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class HomeBookingProjection {
    String name;
    String time;
    String id;

    public HomeBookingTm toTM() {
        return new HomeBookingTm(
                this.name,
                this.time,
                this.id
        );
    }
}
