package lk.ijse.lavishStyloo.dto.projection;

import lk.ijse.lavishStyloo.dto.tm.CustomerOrderDetailsTm;
import lombok.*;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/8/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerOrderDetailsProjection {
    String code;
    String product;
    String price;
    String qty;

    public CustomerOrderDetailsTm toTM() {
        return new CustomerOrderDetailsTm(
                this.code,
                this.product,
                this.price,
                this.qty
        );
    }
}
