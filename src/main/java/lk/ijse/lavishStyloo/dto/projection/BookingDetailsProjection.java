package lk.ijse.lavishStyloo.dto.projection;

import lk.ijse.lavishStyloo.dto.tm.BookingDetailsTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BookingDetailsProjection {
    String treatment;
    String employee;
    String nic;
    String charge;

    public BookingDetailsTm toTM() {
        return new BookingDetailsTm(
                this.treatment,
                this.employee,
                this.nic,
                this.charge
        );
    }
}
