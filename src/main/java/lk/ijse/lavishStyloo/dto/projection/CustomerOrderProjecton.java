package lk.ijse.lavishStyloo.dto.projection;

import lk.ijse.lavishStyloo.dto.tm.CustomerOrderTm;
import lombok.*;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/8/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerOrderProjecton {
    String customerOrderId;
    String customerId;
    String name;
    String date;
    String time;
    String total;
    String count;

    public CustomerOrderTm toTm() {
        return new CustomerOrderTm(
                this.customerOrderId,
                this.customerId,
                this.name,
                this.date,
                this.time,
                this.total,
                this.count
        );
    }
}
