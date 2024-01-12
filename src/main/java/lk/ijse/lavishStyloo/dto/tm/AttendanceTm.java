package lk.ijse.lavishStyloo.dto.tm;

import lombok.*;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/6/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AttendanceTm {
    String nic;
    String name;
    String address;
    String contact;
    String date;
    String inTime;
    String outTime;
}
