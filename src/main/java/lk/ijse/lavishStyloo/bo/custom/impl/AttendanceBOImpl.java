package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.AttendanceBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.AttendanceDAO;
import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.dto.AttendanceDTO;
import lk.ijse.lavishStyloo.dto.projection.AttendanceProjection;
import lk.ijse.lavishStyloo.dto.tm.AttendanceTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class AttendanceBOImpl implements AttendanceBO {
    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    public boolean save(AttendanceDTO attendanceDTO) throws SQLException, ClassNotFoundException {
        return attendanceDAO.save(attendanceDTO.toEntity());
    }

    public List<AttendanceTm> findByDate(String date) throws SQLException, ClassNotFoundException {
        return toTm(queryDAO.findByDate(date));
    }

    public AttendanceDTO findAttendanceByDateAndNic(String nic, String date) throws SQLException, ClassNotFoundException {
        return attendanceDAO.findAttendanceByDateAndNic(nic, date).toDTO();
    }

    public String countAttendanceByDate(String date) throws SQLException, ClassNotFoundException {
        return attendanceDAO.countAttendanceByDate(date);
    }

    public List<AttendanceTm> findByDateAndNameAndNic(String dateNow, String text) throws SQLException, ClassNotFoundException {
        return toTm(queryDAO.findByDateAndNameAndNic(dateNow, text));
    }

    public String getEmployee(String nic) throws SQLException, ClassNotFoundException {
        return attendanceDAO.getEmployee(nic);
    }

    public boolean setOutTime(String nic) throws SQLException, ClassNotFoundException {
        return attendanceDAO.setOutTime(nic);
    }

    public boolean isOutTimeUpdated(String nic) throws SQLException, ClassNotFoundException {
        return attendanceDAO.isOutTimeUpdated(nic);
    }

    public List<AttendanceTm> toTm(List<AttendanceProjection> list) throws SQLException {
        List<AttendanceTm> tmList = new ArrayList<>();
        for (AttendanceProjection projection : list) {
            AttendanceTm tm = new AttendanceTm();
            tm.setNic(projection.getNic());
            tm.setName(projection.getName());
            tm.setAddress(projection.getAddress());
            tm.setContact(projection.getContact());
            tm.setDate(projection.getDate());
            tm.setInTime(projection.getInTime());
            tm.setOutTime(projection.getOutTime());

            tmList.add(tm);
        }
        return tmList;
    }
}
