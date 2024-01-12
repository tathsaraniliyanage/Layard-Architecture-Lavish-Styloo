package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.entity.BookingDetails;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface BookingDetailsDAO extends SuperDAO {

    boolean save(BookingDetails bookingDetails) throws SQLException, ClassNotFoundException;

    boolean save(List<BookingDetails> list) throws SQLException, ClassNotFoundException;

}
