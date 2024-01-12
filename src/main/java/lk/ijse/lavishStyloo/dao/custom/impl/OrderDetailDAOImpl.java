package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.custom.OrderDetailsDAO;
import lk.ijse.lavishStyloo.entity.OrderDetails;
import lk.ijse.lavishStyloo.dao.CrudUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class OrderDetailDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean save(List<OrderDetails> list) throws SQLException, ClassNotFoundException {
        for (OrderDetails orderDetails : list) {
            boolean isSaved = save(orderDetails);
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
      return   CrudUtil.crudUtil("INSERT INTO order_details VALUES (?,?,?,?)",
                orderDetails.getProduct_code(),
                orderDetails.getCust_oid(),
                orderDetails.getPrice(),
                orderDetails.getQty()
        );
    }
}
