package lk.ijse.lavishStyloo.dto.tm;

import lombok.*;

import java.util.List;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/21/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupperOrderDetailsTm {
    String code;
    String product;
    String price;
    String qty;
}
