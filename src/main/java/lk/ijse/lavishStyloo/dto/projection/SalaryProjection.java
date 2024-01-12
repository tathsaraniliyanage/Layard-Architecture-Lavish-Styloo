package lk.ijse.lavishStyloo.dto.projection;

import lk.ijse.lavishStyloo.dto.tm.SalaryTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/25/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class SalaryProjection {
        String nic;
        String name;
        String address;
        String mail;
        String contact;
        double bones;
        double salary;

    public SalaryTm toTm() {
        return new SalaryTm(
                this.nic,
                this.name,
                this.address,
                this.mail,
                this.contact,
                this.bones,
                this.salary
        );
    }
}
