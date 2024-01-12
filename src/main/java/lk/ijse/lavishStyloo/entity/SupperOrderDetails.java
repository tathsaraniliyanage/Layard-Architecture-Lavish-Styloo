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
public class SupperOrderDetails {
    private String sup_oid;
    private String product_code;
    private String price;
    private String qty;

}
