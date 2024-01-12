package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.SupperOrderDetails;
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
public class SupperOrderDetailsDTO {
    private String sup_oid;
    private String product_code;
    private String price;
    private String qty;

    public SupperOrderDetails toEntity() {
        return new SupperOrderDetails(
                this.sup_oid,
                this.product_code,
                this.price,
                this.qty
        );
    }
}
