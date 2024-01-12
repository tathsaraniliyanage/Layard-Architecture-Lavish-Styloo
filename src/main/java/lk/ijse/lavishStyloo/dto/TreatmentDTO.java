package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Treatment;
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

public class TreatmentDTO {
    private String treat_id;
    private double price;
    private String category;
    private String treatment;
    private String description;

    public Treatment toEntity() {
        return new Treatment(
                this.treat_id,
                this.price,
                this.category,
                this.treatment,
                this.description
        );
    }
}
