package lk.ijse.lavishStyloo.entity;

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
public class BookingDetails {
    private String booking_id;
    private String nic;
    private String treat_id;
    private double charge;
}
