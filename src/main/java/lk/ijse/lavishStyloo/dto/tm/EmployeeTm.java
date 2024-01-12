package lk.ijse.lavishStyloo.dto.tm;

import lombok.*;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/23/2023
 */
@AllArgsConstructor
@ToString
@Data
public class EmployeeTm extends RemoveButton {
    private String id;
    private String name;
    private String address;
    private String email;
    private String contact;

    public EmployeeTm() {
        super.setEmployeeTm(this);
    }
}
