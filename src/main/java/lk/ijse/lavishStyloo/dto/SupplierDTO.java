package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Supplier;
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
public class SupplierDTO {
    private String supplier_id;
    private String supplier_name;
    private String company;
    private String email;
    private String contact;
    private String location;

    public Supplier toEntity() {
        return new Supplier(
                this.supplier_id,
                this.supplier_name,
                this.company,
                this.email,
                this.contact,
                this.location
        );
    }
}
