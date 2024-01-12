package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.BookingDetailsDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */


public interface BookingDetailsBO extends SuperBO {
    boolean save(List<BookingDetailsDTO> list) throws SQLException, ClassNotFoundException;
}
