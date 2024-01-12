package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.BookingDetailsBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.BookingDetailsDAO;
import lk.ijse.lavishStyloo.dto.BookingDetailsDTO;
import lk.ijse.lavishStyloo.entity.BookingDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class BookingDetailsBOImpl implements BookingDetailsBO {

    BookingDetailsDAO bookingDetailsDAO = (BookingDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING_DETAIL);

    public boolean save(List<BookingDetailsDTO> list) throws SQLException, ClassNotFoundException {
        return bookingDetailsDAO.save(toEntity(list));
    }

    private List<BookingDetails> toEntity(List<BookingDetailsDTO> list) {
        ArrayList<BookingDetails> details = new ArrayList<>();
        for (BookingDetailsDTO dto : list) {
            details.add(dto.toEntity());
        }
        return details;
    }
}
