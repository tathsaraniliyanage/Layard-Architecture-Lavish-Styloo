package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.BookingDTO;
import lk.ijse.lavishStyloo.dto.BookingDetailsDTO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.dto.tm.BookingDetailsTm;
import lk.ijse.lavishStyloo.dto.tm.BookingTM;
import lk.ijse.lavishStyloo.dto.tm.HomeBookingTm;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface BookingBO extends SuperBO {

    String countBooking() throws SQLException, ClassNotFoundException;

    String getNextId() throws SQLException, ClassNotFoundException;

    List<BookingDTO> findAll() throws SQLException, ClassNotFoundException;

    boolean booking(BookingDTO bookingDTO, List<BookingDetailsDTO> list) throws SQLException, ClassNotFoundException;

    List<ReportCm> getAppointment() throws SQLException, ClassNotFoundException;

    String CountBooking() throws SQLException, ClassNotFoundException;

    String CountBookingByDate(String date) throws SQLException, ClassNotFoundException;

    List<BookingTM> findBooking() throws SQLException, ClassNotFoundException;

    List<BookingTM> findBookingByDate(String date) throws SQLException, ClassNotFoundException;

    List<BookingDetailsTm> findBookingDetailsByBookingId(String bookingId) throws SQLException, ClassNotFoundException;

    String UnCompliedCount() throws SQLException, ClassNotFoundException;

    String CompliedCount() throws SQLException, ClassNotFoundException;

    String PendingCount() throws SQLException, ClassNotFoundException;

    List<ReportCm> getYearlyAppointment(String year) throws SQLException, ClassNotFoundException;

    List<ReportCm> getMonthlyAppointment(String year, String moth) throws SQLException, ClassNotFoundException;

    List<ReportCm> getDayAppointment(String year, String month) throws SQLException, ClassNotFoundException;

    BookingDTO findBookingByID(String bookingId) throws SQLException, ClassNotFoundException;

    List<HomeBookingTm> findNameTimeId() throws SQLException, ClassNotFoundException;

    List<HomeBookingTm> findNameTimeId(String txt) throws SQLException, ClassNotFoundException;

    List<String> findIdByDateForPayment() throws SQLException, ClassNotFoundException;

}
