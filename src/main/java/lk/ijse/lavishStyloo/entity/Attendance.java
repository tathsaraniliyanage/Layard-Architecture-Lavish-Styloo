package lk.ijse.lavishStyloo.entity;

import lk.ijse.lavishStyloo.dto.AttendanceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Attendance {
    private String date;
    private String in_time;
    private String out_time;
    private String nic;

    public AttendanceDTO toDTO() {
        return new AttendanceDTO(
                this.date,
                this.in_time,
                this.out_time,
                this.nic
        );
    }
}
