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

public class OrderDetails {
    private String product_code;
    private String cust_oid;
    private double price;
    private int qty;

}
