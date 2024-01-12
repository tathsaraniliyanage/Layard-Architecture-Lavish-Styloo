package lk.ijse.lavishStyloo.dto.tm;

import lombok.*;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/8/2023
 */
@AllArgsConstructor
@ToString
@Setter
@Getter
public class ProductTm extends RemoveButton {
    private String product_code;
    private String product;
    private String description;
    private String unit_price;
    private String qty;
    private String img;

    public ProductTm() {
        super.setProductTm(this);
    }
}
