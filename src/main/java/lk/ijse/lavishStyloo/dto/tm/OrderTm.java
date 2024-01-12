package lk.ijse.lavishStyloo.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderTm {
    private String itemCode;
    private String product;
    private String unitPrice;
    private String Price;
    private String qty;
}
