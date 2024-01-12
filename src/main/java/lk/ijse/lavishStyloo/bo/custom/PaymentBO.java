package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public interface PaymentBO extends SuperBO {

    String nextID() throws SQLException, ClassNotFoundException;

    List<String> findId() throws SQLException, ClassNotFoundException;

    boolean savePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException;
}
