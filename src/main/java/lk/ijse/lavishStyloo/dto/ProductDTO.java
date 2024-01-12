package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Product;
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
public class ProductDTO {
    private String product_code;
    private String product;
    private String description;
    private String unit_price;
    private String qty;
    private String img;

    public Product toEntity() {
        return new Product(
                this.product_code,
                this.product,
                this.description,
                this.unit_price,
                this.qty,
                this.img
        );
    }
}
