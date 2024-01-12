package lk.ijse.lavishStyloo.entity;

import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lombok.*;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private String customer_id;
    private String first_name;
    private String last_name;
    private String email;
    private String city;
    private String lane;
    private String street;
    private String contact;


    public CustomerDTO toDTO() {
        return new CustomerDTO(
                this.customer_id,
                this.first_name,
                this.last_name,
                this.email,
                this.city,
                this.lane,
                this.street,
                this.contact
        );
    }
}
