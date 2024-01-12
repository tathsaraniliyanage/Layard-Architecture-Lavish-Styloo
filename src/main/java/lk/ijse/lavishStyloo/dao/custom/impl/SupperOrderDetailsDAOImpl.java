package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.SupperOrderDetailsDAO;
import lk.ijse.lavishStyloo.entity.SupperOrderDetails;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class SupperOrderDetailsDAOImpl implements SupperOrderDetailsDAO {
    @Override
    public boolean save(List<SupperOrderDetails> supperOrderDetails) throws SQLException, ClassNotFoundException {
        for (SupperOrderDetails orderDetails : supperOrderDetails) {
            boolean isSaved = save(orderDetails);
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(SupperOrderDetails supperOrderDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO supplier_order_details VALUES (?,?,?,?)",
                supperOrderDetails.getSup_oid(),
                supperOrderDetails.getProduct_code(),
                supperOrderDetails.getPrice(),
                supperOrderDetails.getQty()
        );
    }
}
