package lk.ijse.lavishStyloo.dto;

import lk.ijse.lavishStyloo.entity.Attendance;
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
public class AttendanceDTO {
    private String date;
    private String in_time;
    private String out_time;
    private String nic;

    public Attendance toEntity() {
        Attendance attendance = new Attendance();
        attendance.setDate(this.date);
        attendance.setIn_time(this.in_time);
        attendance.setOut_time(this.out_time);
        attendance.setNic(this.nic);
        return attendance;
    }

}
