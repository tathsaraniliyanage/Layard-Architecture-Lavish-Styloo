package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.entity.Attendance;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface AttendanceDAO extends SuperDAO {

    boolean save(Attendance attendance) throws SQLException, ClassNotFoundException;

    Attendance findAttendanceByDateAndNic(String nic, String date) throws SQLException, ClassNotFoundException;

    Attendance toEntity(ResultSet result) throws SQLException;

    String countAttendanceByDate(String date) throws SQLException, ClassNotFoundException;

    String getEmployee(String nic) throws SQLException, ClassNotFoundException;

    boolean setOutTime(String nic) throws SQLException, ClassNotFoundException;

    boolean isOutTimeUpdated(String nic) throws SQLException, ClassNotFoundException;
}
