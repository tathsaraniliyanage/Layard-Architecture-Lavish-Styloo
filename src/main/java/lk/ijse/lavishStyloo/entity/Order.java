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

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Order {
    private String cust_oid;
    private String cust_id;
    private String date;
    private String time;
    private double total;

}
