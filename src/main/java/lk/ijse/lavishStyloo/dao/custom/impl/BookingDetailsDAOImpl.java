package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.BookingDetailsDAO;
import lk.ijse.lavishStyloo.entity.BookingDetails;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class BookingDetailsDAOImpl implements BookingDetailsDAO {

    public boolean save(List<BookingDetails> list) throws SQLException, ClassNotFoundException {
        for (BookingDetails bookingDetails : list) {
            boolean save = save(bookingDetails);
            if (save == false) {
                return false;
            }
        }
        return true;
    }

    public boolean save(BookingDetails bookingDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO booking_details VALUES (?,?,?,?)",
                bookingDetails.getBooking_id(),
                bookingDetails.getNic(),
                bookingDetails.getTreat_id(),
                bookingDetails.getCharge()
        );

    }
}
