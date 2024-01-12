package lk.ijse.lavishStyloo.dto.projection;

import lk.ijse.lavishStyloo.dto.tm.SupperOrderTm;
import lombok.*;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/21/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupperOrderProjection {
    private String supperOrderId;
    private String supperId;
    private String dear;
    private String date;
    private String time;
    private String total;
    private String count;

    public SupperOrderTm toTM() {
        return new SupperOrderTm(
                this.supperOrderId,
                this.supperId,
                this.dear,
                this.date,
                this.time,
                this.total,
                this.count
        );
    }
}
