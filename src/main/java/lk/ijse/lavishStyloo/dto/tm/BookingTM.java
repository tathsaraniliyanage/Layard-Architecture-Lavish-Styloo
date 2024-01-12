package lk.ijse.lavishStyloo.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/25/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BookingTM {

    private String bookingId;
    private String cusId;
    private String customer;
    private String date;
    private String time;
    private String total;
    private String bookingStart;
    private String bookingEnd;
    private Button option;

}
