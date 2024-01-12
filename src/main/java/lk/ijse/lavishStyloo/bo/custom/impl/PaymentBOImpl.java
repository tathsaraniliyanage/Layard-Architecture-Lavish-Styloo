package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.PaymentBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.PaymentDAO;
import lk.ijse.lavishStyloo.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    public String nextID() throws SQLException, ClassNotFoundException {
        List<String> ids = findId();
        String oldId = null;
        for (String s : ids) {
            oldId = s;
        }
        int lastIndex;
        try {
            String[] split = oldId.split("P00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "P001";
        }
        lastIndex++;
        return "P00" + lastIndex;
    }

    public List<String> findId() throws SQLException, ClassNotFoundException {
        return paymentDAO.findId();
    }

    public boolean savePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(dto.toEntity());
    }
}
