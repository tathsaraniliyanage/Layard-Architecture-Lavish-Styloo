package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.AttendanceDTO;
import lk.ijse.lavishStyloo.dto.projection.AttendanceProjection;
import lk.ijse.lavishStyloo.dto.tm.AttendanceTm;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface AttendanceBO extends SuperBO {

    boolean save(AttendanceDTO attendanceDTO) throws SQLException, ClassNotFoundException;

    List<AttendanceTm> findByDate(String date) throws SQLException, ClassNotFoundException;

    AttendanceDTO findAttendanceByDateAndNic(String nic, String date) throws SQLException, ClassNotFoundException;

    String countAttendanceByDate(String date) throws SQLException, ClassNotFoundException;

    List<AttendanceTm> findByDateAndNameAndNic(String dateNow, String text) throws SQLException, ClassNotFoundException;

    String getEmployee(String nic) throws SQLException, ClassNotFoundException;

    boolean setOutTime(String nic) throws SQLException, ClassNotFoundException;

    boolean isOutTimeUpdated(String nic) throws SQLException, ClassNotFoundException;

    List<AttendanceTm> toTm(List<AttendanceProjection> list) throws SQLException;
}
