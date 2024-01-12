package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SalaryDTO {
    private String s_id;
    private String date;
    private String time;
    private double salary;
    private double bonus;
    private String nic;

    public Salary toEntity() {
        return new Salary(
                this.s_id,
                this.date,
                this.time,
                this.salary,
                this.bonus,
                this.nic
        );
    }
}
