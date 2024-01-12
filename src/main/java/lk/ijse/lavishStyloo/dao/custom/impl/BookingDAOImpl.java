package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.BookingDAO;
import lk.ijse.lavishStyloo.dao.custom.BookingDetailsDAO;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.entity.Booking;
import lk.ijse.lavishStyloo.entity.BookingDetails;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class BookingDAOImpl implements BookingDAO {

    BookingDetailsDAO bookingDetailsDAO = (BookingDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING_DETAIL);

    public boolean save(Booking booking) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO booking VALUES (?,?,?,?,?,?,?)",
                booking.getBooking_id(),
                booking.getDate(),
                booking.getTime(),
                booking.getTotal(),
                booking.getCus_id(),
                booking.getBooking_start(),
                booking.getBooking_end()
        );
    }

    public Booking findBookingByID(String bookingId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM  booking WHERE booking_id=?", bookingId);
        return toDTO(resultSet);
    }

    public Booking toDTO(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        if (resultSet.next()) {
            booking.setBooking_id(resultSet.getString(1));
            booking.setDate(resultSet.getString(2));
            booking.setTime(resultSet.getString(3));
            booking.setTotal(resultSet.getString(4));
            booking.setCus_id(resultSet.getString(5));
            booking.setBooking_start(resultSet.getString(6));
            booking.setBooking_end(resultSet.getString(7));
        }
        return booking;
    }

    public String countBooking() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT COUNT(booking_id) FROM booking");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        List<Booking> ids = findAll();
        String oldId = null;
        for (Booking bookingDTO : ids) {
            oldId = bookingDTO.getBooking_id();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("B00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "B001";
        }
        lastIndex++;
        return "B00" + lastIndex;
    }

    public List<Booking> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT  * FROM booking");
        return toEntities(resultSet);
    }

    public List<Booking> toEntities(ResultSet resultSet) throws SQLException {
        List<Booking> list = new ArrayList<>();
        while (resultSet.next()) {
            Booking booking = new Booking();
            booking.setBooking_id(resultSet.getString(1));
            booking.setDate(resultSet.getString(2));
            booking.setTime(resultSet.getString(3));
            booking.setTotal(resultSet.getString(4));
            booking.setCus_id(resultSet.getString(5));
            booking.setBooking_start(resultSet.getString(6));
            booking.setBooking_end(resultSet.getString(7));

            list.add(booking);
        }
        return list;
    }

    public boolean booking(Booking booking, List<BookingDetails> list) throws SQLException, ClassNotFoundException {
        /**
         * get connection
         * */

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSaved = save(booking);
            if (isSaved) {
                boolean isSavedDetails = bookingDetailsDAO.save(list);
                if (isSavedDetails) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public List<ReportCm> getAppointment() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT MONTH(date) AS month, SUM(total)as num FROM booking GROUP BY month");
        List<ReportCm> list = new ArrayList<>();
        while (result.next()) {
            ReportCm reportCm = new ReportCm();
            reportCm.setTitle(result.getString(1));
            reportCm.setValue(result.getDouble(2));
            list.add(reportCm);
        }
        return list;
    }

    public String CountBooking() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM  booking";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public String CountBookingByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM  booking WHERE date=?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, date);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public String UnCompliedCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(b.booking_id) FROM booking b WHERE date=CURDATE() AND b.booking_id NOT IN (SELECT p.booking_id FROM payment p WHERE p.date=CURDATE())";
        ResultSet result = CrudUtil.crudUtil(sql);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

    public String PendingCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(b.booking_id) FROM booking b WHERE date=CURDATE() AND b.booking_id NOT IN (SELECT p.booking_id FROM payment p WHERE p.date=CURDATE())";
        ResultSet result = CrudUtil.crudUtil(sql);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

    public List<ReportCm> getYearlyAppointment(String year) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        List<ReportCm> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM booking WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)", year, (i < 10 ? ("0" + i) : i));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i - 1)]);
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            } else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i - 1)]);
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }

    public List<ReportCm> getMonthlyAppointment(String year, String moth) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int month = 0;
        for (int i = 0; i < allMonth.length; i++) {
            if (allMonth[i].equals(moth)) {
                month = i;
                break;
            }
        }
        int days = DateTimeUtil.getDays(Integer.parseInt(year), (month + 1));
        List<ReportCm> list = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM booking WHERE YEAR(date)=? and MONTH(date)= ? and DAY(date)=? GROUP BY DAY(date)", year, (month + 1), (i < 10 ? ("0" + i) : i));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(String.valueOf((i - 1)));
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            } else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(String.valueOf((i)));
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }

    public List<ReportCm> getDayAppointment(String year, String month) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int thisMonth = 0;
        for (int i = 0; i < allMonth.length; i++) {
            if (allMonth[i].equals(month)) {
                thisMonth = i;
                break;
            }
        }
        List<ReportCm> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM booking WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)", year, (thisMonth + 1));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i - 1)]);
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            } else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i - 1)]);
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }

    public List<String> findIdByDate() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT booking_id FROM booking WHERE date = CURDATE()");
        List<String> list = new ArrayList<>();
        if (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public List<String> findIdByDateForPayment() throws SQLException, ClassNotFoundException {
        String sql = "SELECT b.booking_id FROM booking b WHERE date=CURDATE() AND b.booking_id NOT IN (SELECT p.booking_id FROM payment p WHERE p.date=CURDATE())";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }
}
