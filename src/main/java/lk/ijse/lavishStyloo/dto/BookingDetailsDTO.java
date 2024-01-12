package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.BookingDetails;
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
public class BookingDetailsDTO {
    private String booking_id;
    private String nic;
    private String treat_id;
    private double charge;

    public BookingDetails toEntity() {
        return new BookingDetails(
                this.booking_id,
                this.nic,
                this.treat_id,
                this.charge
        );
    }
}
