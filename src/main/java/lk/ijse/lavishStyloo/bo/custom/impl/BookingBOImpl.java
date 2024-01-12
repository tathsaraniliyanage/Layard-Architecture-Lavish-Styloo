package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.BookingBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.BookingDAO;
import lk.ijse.lavishStyloo.dao.custom.BookingDetailsDAO;
import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.BookingDTO;
import lk.ijse.lavishStyloo.dto.BookingDetailsDTO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.dto.projection.BookingDetailsProjection;
import lk.ijse.lavishStyloo.dto.projection.BookingProjection;
import lk.ijse.lavishStyloo.dto.projection.HomeBookingProjection;
import lk.ijse.lavishStyloo.dto.tm.BookingDetailsTm;
import lk.ijse.lavishStyloo.dto.tm.BookingTM;
import lk.ijse.lavishStyloo.dto.tm.HomeBookingTm;
import lk.ijse.lavishStyloo.entity.Booking;
import lk.ijse.lavishStyloo.entity.BookingDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class BookingBOImpl implements BookingBO {

    BookingDAO bookingDAO = (BookingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING);
    BookingDetailsDAO bookingDetailsDAO = (BookingDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING_DETAIL);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    public String countBooking() throws SQLException, ClassNotFoundException {
        return bookingDAO.countBooking();
    }

    public List<BookingDTO> findAll() throws SQLException, ClassNotFoundException {
        return toDTOList(bookingDAO.findAll());
    }

    public boolean booking(BookingDTO bookingDTO, List<BookingDetailsDTO> list) throws SQLException, ClassNotFoundException {
        /**
         * get connection
         * */

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSaved = bookingDAO.save(bookingDTO.toEntity());
            if (isSaved) {
                boolean isSavedDetails = bookingDetailsDAO.save(toBookingDetails(list));
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
        return bookingDAO.getAppointment();
    }

    public String CountBooking() throws SQLException, ClassNotFoundException {
        return bookingDAO.CountBooking();
    }

    public String CountBookingByDate(String date) throws SQLException, ClassNotFoundException {
        return bookingDAO.CountBookingByDate(date);
    }

    public List<BookingTM> findBooking() throws SQLException, ClassNotFoundException {
        return toTms(queryDAO.findBooking());
    }

    public List<BookingTM> findBookingByDate(String date) throws SQLException, ClassNotFoundException {
        return toTms(queryDAO.findBookingByDate(date));
    }

    public List<BookingDetailsTm> findBookingDetailsByBookingId(String bookingId) throws SQLException, ClassNotFoundException {
        return toDetailsTms(queryDAO.findBookingDetailsByBookingId(bookingId));
    }

    public String UnCompliedCount() throws SQLException, ClassNotFoundException {
        return bookingDAO.UnCompliedCount();
    }

    public String CompliedCount() throws SQLException, ClassNotFoundException {
        return queryDAO.CompliedCount();
    }

    public String PendingCount() throws SQLException, ClassNotFoundException {
        return bookingDAO.PendingCount();
    }

    public List<ReportCm> getYearlyAppointment(String year) throws SQLException, ClassNotFoundException {
        return bookingDAO.getYearlyAppointment(year);
    }

    public List<ReportCm> getMonthlyAppointment(String year, String moth) throws SQLException, ClassNotFoundException {
        return bookingDAO.getMonthlyAppointment(year, moth);
    }

    public List<ReportCm> getDayAppointment(String year, String month) throws SQLException, ClassNotFoundException {
        return bookingDAO.getDayAppointment(year, month);
    }

    public List<String> findIdByDate() throws SQLException, ClassNotFoundException {
        return bookingDAO.findIdByDate();
    }

    public BookingDTO findBookingByID(String bookingId) throws SQLException, ClassNotFoundException {
        return bookingDAO.findBookingByID(bookingId).toDTO();
    }

    public List<HomeBookingTm> findNameTimeId() throws SQLException, ClassNotFoundException {
        return toHomeTm(queryDAO.findNameTimeId());
    }

    public List<HomeBookingTm> findNameTimeId(String txt) throws SQLException, ClassNotFoundException {
        return toHomeTm(queryDAO.findNameTimeId(txt));
    }

    public List<String> findIdByDateForPayment() throws SQLException, ClassNotFoundException {
        return bookingDAO.findIdByDateForPayment();
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        List<BookingDTO> ids = findAll();
        String oldId = null;
        for (BookingDTO bookingDTO : ids) {
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

    public List<BookingDTO> toDTOList(List<Booking> list) throws SQLException {
        List<BookingDTO> dtoList = new ArrayList<>();
        for (Booking booking : list) {
            dtoList.add(booking.toDTO());
        }
        return dtoList;
    }

    private List<HomeBookingTm> toHomeTm(List<HomeBookingProjection> projectionList) throws SQLException {
        List<HomeBookingTm> list = new ArrayList<>();
        for (HomeBookingProjection homeBookingProjection : projectionList) {
            list.add(homeBookingProjection.toTM());
        }
        return list;
    }

    private List<BookingTM> toTms(List<BookingProjection> booking) {
        List<BookingTM> list = new ArrayList<>();
        for (BookingProjection projection : booking) {
            list.add(projection.toTM());
        }
        return list;
    }

    private List<BookingDetails> toBookingDetails(List<BookingDetailsDTO> list) {
        ArrayList<BookingDetails> details = new ArrayList<>();
        for (BookingDetailsDTO dto : list) {
            details.add(dto.toEntity());
        }
        return details;
    }

    private List<BookingDetailsTm> toDetailsTms(List<BookingDetailsProjection> list) throws SQLException {
        List<BookingDetailsTm> tms = new ArrayList<>();
        for (BookingDetailsProjection projection : list) {
            tms.add(projection.toTM());
        }
        return tms;
    }

}
