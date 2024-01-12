package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Order;
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
public class OrderDTO {
    private String cust_oid;
    private String cust_id;
    private String date;
    private String time;
    private double total;

    public Order toEntity() {
        return new Order(
                this.cust_oid,
                this.cust_id,
                this.date,
                this.time,
                this.total
        );
    }
}
