package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.User;
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

public class UserDTO {
    private String userName;
    private String password;
    private String nic;

    public User toEntity() {
        return new User(
                this.userName,
                this.password,
                this.nic
        );
    }
}
