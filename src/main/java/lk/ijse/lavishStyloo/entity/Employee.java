package lk.ijse.lavishStyloo.entity;

import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lombok.*;

import java.time.LocalDate;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class Employee {
    private String nic;
    private String first_name;
    private String last_name;
    private String email;
    private String city;
    private String lane;
    private String street;
    private String contact;
    private LocalDate dateOfBirth;
    private String gender;
    private String role;

    public EmployeeDTO toEntity() {

        return new EmployeeDTO(
                this.nic,
                this.first_name,
                this.last_name,
                this.email,
                this.city,
                this.lane,
                this.street,
                this.contact,
                this.dateOfBirth,
                this.gender,
                this.role
        );

    }
}
