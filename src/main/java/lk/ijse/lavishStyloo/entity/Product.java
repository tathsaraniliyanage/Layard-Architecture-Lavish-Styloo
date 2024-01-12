package lk.ijse.lavishStyloo.entity;

import lk.ijse.lavishStyloo.dto.ProductDTO;
import lombok.*;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class Product {
    private String product_code;
    private String product;
    private String description;
    private String unit_price;
    private String qty;
    private String img;

    public ProductDTO toDTO() {
        return new ProductDTO(
                this.product_code,
                this.product,
                this.description,
                this.unit_price,
                this.qty,
                this.img
        );
    }
}
