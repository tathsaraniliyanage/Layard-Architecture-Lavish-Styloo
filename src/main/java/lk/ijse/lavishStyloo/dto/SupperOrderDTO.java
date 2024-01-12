package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.SupperOrder;
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
public class SupperOrderDTO {
    private String sup_oid;
    private String supplier_id;
    private String date;
    private String time;
    private double total;

    public SupperOrder toEntity() {
        return new SupperOrder(
                this.sup_oid,
                this.supplier_id,
                this.date,
                this.time,
                this.total
        );
    }
}
