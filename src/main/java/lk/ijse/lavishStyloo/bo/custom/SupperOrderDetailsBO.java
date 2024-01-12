package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.tm.SupperOrderDetailsTm;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface SupperOrderDetailsBO extends SuperBO {
    List<SupperOrderDetailsTm> findCustomerOrderDetailsByOrderId(String supperOrderId) throws SQLException, ClassNotFoundException;
}
