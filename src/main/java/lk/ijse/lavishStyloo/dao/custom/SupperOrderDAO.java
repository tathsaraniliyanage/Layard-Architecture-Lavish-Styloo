package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.entity.SupperOrder;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface SupperOrderDAO extends SuperDAO {

    boolean save(SupperOrder supperOrder) throws SQLException, ClassNotFoundException;

    String CountCustomerOrder() throws SQLException, ClassNotFoundException;

    String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException;

    List<ReportCm> getSupplierOrder() throws SQLException, ClassNotFoundException;

    String nextId() throws SQLException, ClassNotFoundException;

    List<ReportCm> getYearlySupplierOrder(String year) throws SQLException, ClassNotFoundException;

    List<ReportCm> getMonthlySupplierOrder(String year, String moth) throws SQLException, ClassNotFoundException;

    List<ReportCm> getDaySupplierOrder(String year, String month) throws SQLException, ClassNotFoundException;
}
