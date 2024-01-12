package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface OrderDetailsDAO extends SuperDAO {
    boolean save(List<OrderDetails> orderDetails) throws SQLException, ClassNotFoundException;
    boolean save(OrderDetails orderDetails) throws SQLException, ClassNotFoundException;
}
