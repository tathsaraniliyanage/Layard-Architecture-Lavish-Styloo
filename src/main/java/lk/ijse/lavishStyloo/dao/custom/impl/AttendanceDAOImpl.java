package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.AttendanceDAO;
import lk.ijse.lavishStyloo.entity.Attendance;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class AttendanceDAOImpl implements AttendanceDAO {

    public boolean save(Attendance attendance) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO attendance VALUES( ?,?,?,?)";
        return CrudUtil.crudUtil(sql, attendance.getDate(), attendance.getIn_time(), attendance.getOut_time(), attendance.getNic());
    }

    public Attendance findAttendanceByDateAndNic(String nic, String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM attendance where date=? AND nic=?";
        ResultSet result = CrudUtil.crudUtil(sql, date, nic);
        return toEntity(result);
    }

    public String countAttendanceByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(nic) FROM attendance where date=?";
        ResultSet result = CrudUtil.crudUtil(sql, date);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

    public String getEmployee(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(nic) from attendance WHERE date between  CURDATE() AND (SELECT date from salary WHERE nic ORDER BY date DESC LIMIT 1)");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "NOTHING ATTENDANCE";
    }

    public boolean setOutTime(String nic) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE attendance SET  out_time =CURTIME() WHERE nic =? and date =CURDATE()", nic);
    }

    public boolean isOutTimeUpdated(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT out_time FROM attendance WHERE nic =? and date =CURDATE()", nic);
        if (resultSet.next()) {
            return resultSet.getString(1).equals("00:00:00");
        }
        return false;
    }

    public Attendance toEntity(ResultSet result) throws SQLException {
        Attendance attendance = new Attendance();
        if (result.next()) {
            attendance.setDate(result.getString(1));
            attendance.setIn_time(result.getString(2));
            attendance.setOut_time(result.getString(3));
            attendance.setNic(result.getString(4));
        }

        return attendance;
    }

}
