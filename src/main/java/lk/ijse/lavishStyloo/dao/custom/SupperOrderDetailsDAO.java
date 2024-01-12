package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.entity.SupperOrderDetails;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface SupperOrderDetailsDAO extends SuperDAO {

    boolean save(List<SupperOrderDetails> supperOrderDetails) throws SQLException, ClassNotFoundException;

    boolean save(SupperOrderDetails supperOrderDetails) throws SQLException, ClassNotFoundException;
}
