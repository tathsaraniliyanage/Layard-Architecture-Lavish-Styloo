package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.OrderDetails;
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

public class OrderDetailsDTO {
    private String product_code;
    private String cust_oid;
    private double price;
    private int qty;

    public OrderDetails toEntity() {
        return new OrderDetails(
                this.product_code,
                this.cust_oid,
                this.price,
                this.qty
        );
    }
}
