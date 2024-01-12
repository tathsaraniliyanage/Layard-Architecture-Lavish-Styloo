package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.entity.Booking;
import lk.ijse.lavishStyloo.entity.BookingDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface BookingDAO extends SuperDAO {

    String countBooking() throws SQLException, ClassNotFoundException;

    String getNextId() throws SQLException, ClassNotFoundException;

    List<Booking> findAll() throws SQLException, ClassNotFoundException;

    List<Booking> toEntities(ResultSet resultSet) throws SQLException;

    boolean booking(lk.ijse.lavishStyloo.entity.Booking booking, List<BookingDetails> list) throws SQLException, ClassNotFoundException;

    boolean save(lk.ijse.lavishStyloo.entity.Booking booking) throws SQLException, ClassNotFoundException;

    List<ReportCm> getAppointment() throws SQLException, ClassNotFoundException;

    String CountBooking() throws SQLException, ClassNotFoundException;

    String CountBookingByDate(String date) throws SQLException, ClassNotFoundException;

    String UnCompliedCount() throws SQLException, ClassNotFoundException;

    String PendingCount() throws SQLException, ClassNotFoundException;

    List<ReportCm> getYearlyAppointment(String year) throws SQLException, ClassNotFoundException;

    List<ReportCm> getMonthlyAppointment(String year, String moth) throws SQLException, ClassNotFoundException;

    List<ReportCm> getDayAppointment(String year, String month) throws SQLException, ClassNotFoundException;

    Booking findBookingByID(String bookingId) throws SQLException, ClassNotFoundException;

    Booking toDTO(ResultSet resultSet) throws SQLException;

    List<String> findIdByDateForPayment() throws SQLException, ClassNotFoundException;

    List<String> findIdByDate() throws SQLException, ClassNotFoundException;

}
