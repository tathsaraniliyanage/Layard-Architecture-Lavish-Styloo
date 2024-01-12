package lk.ijse.lavishStyloo.dto.tm;

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
public class CustomerOrderTm {
    String customerOrderId;
    String customerId;
    String name;
    String date;
    String time;
    String total;
    String count;
}
