package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BookingDTO {
    private String booking_id;
    private String date;
    private String time;
    private String total;
    private String cus_id;
    private String booking_start;
    private String booking_end;

    public Booking toEntity() {
        return new Booking(
                this.booking_id,
                this.date,
                this.time,
                this.total,
                this.cus_id,
                this.booking_start,
                this.booking_end
        );
    }
}




