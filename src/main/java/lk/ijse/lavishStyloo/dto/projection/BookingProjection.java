package lk.ijse.lavishStyloo.dto.projection;

import javafx.scene.control.Button;
import lk.ijse.lavishStyloo.dto.tm.BookingTM;
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
public class BookingProjection {

    private String bookingId;
    private String cusId;
    private String customer;
    private String date;
    private String time;
    private String total;
    private String bookingStart;
    private String bookingEnd;
    private Button option;

    public BookingTM toTM() {
        return new BookingTM(
                this.bookingId,
                this.cusId,
                this.customer,
                this.date,
                this.time,
                this.total,
                this.bookingStart,
                this.bookingEnd,
                this.option
        );
    }
}
