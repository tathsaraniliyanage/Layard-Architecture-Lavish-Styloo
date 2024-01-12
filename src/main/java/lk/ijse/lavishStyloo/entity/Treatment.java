package lk.ijse.lavishStyloo.entity;

import lk.ijse.lavishStyloo.dto.TreatmentDTO;
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

public class Treatment {
    private String treat_id;
    private double price;
    private String category;
    private String treatment;
    private String description;

    public TreatmentDTO toDTO() {
        return new TreatmentDTO(
                this.treat_id,
                this.price,
                this.category,
                this.treatment,
                this.description
        );
    }
}
