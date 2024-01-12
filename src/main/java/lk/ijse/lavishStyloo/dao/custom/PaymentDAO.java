package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.entity.Payment;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public interface PaymentDAO extends SuperDAO {

    List<String> findId() throws SQLException, ClassNotFoundException;

    boolean save(Payment payment) throws SQLException, ClassNotFoundException;

}
