package lk.ijse.lavishStyloo.dto.projection;

import lk.ijse.lavishStyloo.dto.tm.SupperOrderDetailsTm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupperOrderDetailsProjection {
    String code;
    String product;
    String price;
    String qty;

    public SupperOrderDetailsTm toTM() {
        return new SupperOrderDetailsTm(
                this.code,
                this.product,
                this.price,
                this.qty
        );
    }
}