package lk.ijse.lavishStyloo.dto.tm;

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
public class SupperOrderTm {
    private String supperOrderId;
    private String supperId;
    private String dear;
    private String date;
    private String time;
    private String total;
    private String count;
}
