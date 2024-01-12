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
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SupperOrder {
    private String sup_oid;
    private String supplier_id;
    private String date;
    private String time;
    private double total;

}
