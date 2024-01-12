package lk.ijse.lavishStyloo.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BookingDetailsTm {
    String treatment;
    String employee;
    String nic;
    String charge;
}
