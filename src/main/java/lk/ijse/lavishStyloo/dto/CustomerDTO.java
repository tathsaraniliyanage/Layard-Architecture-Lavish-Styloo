package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Customer;
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
public class CustomerDTO {
    private String customer_id;
    private String first_name;
    private String last_name;
    private String email;
    private String city;
    private String lane;
    private String street;
    private String contact;


    public Customer toEntity() {
        return new Customer(
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
