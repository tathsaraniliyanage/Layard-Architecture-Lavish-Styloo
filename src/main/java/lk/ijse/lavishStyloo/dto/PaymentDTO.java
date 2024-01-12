package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PaymentDTO {
    private String payment_id;
    private String net_payment;
    private String date;
    private String time;
    private String booking_id;

    public Payment toEntity() {
        return new Payment(
                this.payment_id,
                this.net_payment,
                this.date,
                this.time,
                this.booking_id
        );
    }
}
